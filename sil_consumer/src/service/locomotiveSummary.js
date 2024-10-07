const create = async (data) => {
  // parsing string data from kafka to be json
  data = JSON.parse(data);
  try {
    // insert data to mongodb
    const response = await postgresClient.summaryTest.create({
      data: {
        totalLocomotive: data.totalLocomotive,
        totalOnDuty: data.totalOnDuty,
        totalOnDepot: data.totalOnDepot,
        totalUnderMaintenance: data.totalUnderMaintenance,
        createdAt: new Date(data.createdAt),
        updatedAt: new Date(data.updatedAt),
      },
    });
    console.log("insert data to mongodb successful : ", response);
  } catch (err) {
    console.error("insert data to mongodb failed : ", err);
  }
};

export { create };
