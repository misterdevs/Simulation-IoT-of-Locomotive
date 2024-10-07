import { PrismaClient as PostgresClient } from "../../prisma/generated/postgresql/index.js";
import { PrismaClient as MongoClient } from "../../prisma/generated/mongodb/index.js";

// initialize client
export const postgresClient = new PostgresClient({});
export const mongoClient = new MongoClient({});
