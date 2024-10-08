import { mongoClient } from "../application/database.js";

const create = async (data) => {
  // parsing string data from kafka to be json
  data = JSON.parse(data);
  // console.log(data);
  try {
    // insert data to mongodb
    const response = await mongoClient.summaryTests.create({
      data: {
        totalLocomotive: data.totalLocomotive,
        status: { create: data.status },
        createdAt: new Date(data.createdAt),
        updatedAt: new Date(data.updatedAt),
      },
    });

    //retrieve detail status
    const getStatus = await mongoClient.statusTests.findMany({
      where: {
        summaryTestId: response.id,
      },
    });
    console.log("insert data to mongodb successful : ", {
      ...response,
      status: getStatus,
    });
  } catch (err) {
    console.error("insert data to mongodb failed : ", err);
  }
};

export { create };
