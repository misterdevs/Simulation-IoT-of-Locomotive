import { Kafka } from "kafkajs";
import * as locomotive from "./src/service/locomotive.js";
import * as locomotiveSummary from "./src/service/locomotiveSummary.js";

// kafka initialize
const kafka = new Kafka({
  clientId: "consumer-nodejs",
  brokers: ["localhost:9092"],
});

const consumer = kafka.consumer({ groupId: "consumer-nodejs" });

// topics we need to listen
const topics = [process.env.KAFKA_TOPICS_1, process.env.KAFKA_TOPICS_2];

const run = async () => {
  await consumer.connect();

  await consumer.subscribe({
    topic: topics[0],
    fromBeginning: true,
  });
  await consumer.subscribe({
    topic: topics[1],
    fromBeginning: true,
  });

  // listen to kafka
  await consumer.run({
    eachMessage: async ({ topic, partition, message }) => {
      const messageValue = message.value.toString();

      switch (topic) {
        case topics[0]:
          // handle locomotive data
          locomotive.create(messageValue);
          break;
        case topics[1]:
          //handle locomotive summary data
          locomotiveSummary.create(messageValue);
          break;
        default:
          console.warn(`Received message from unknown topic: ${topic}`);
      }
    },
  });
};

run().catch(console.error);
