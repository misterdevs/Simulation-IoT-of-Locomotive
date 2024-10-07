import { prismaClient } from "../application/database.js";

const create = async (data) => {
  // parsing string data from kafka to be json
  data = JSON.parse(data);
  try {
    // insert data to postgresql
    const response = await prismaClient.locomotive.create({
      data: {
        id: data.id,
        code: data.code,
        name: data.name,
        dimension: data.dimension,
        status_id: data.statusId,
        created_at: new Date(data.createdAt),
      },
    });
    console.log("insert data to postgresql successful : ", response);
  } catch (err) {
    console.error("insert data to postgresql failed : ", err);
  }
};

export { create };
