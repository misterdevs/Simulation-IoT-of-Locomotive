import { useEffect, useState } from "react";
import {
  MdRailwayAlert,
  MdShowChart,
  MdTrain,
  MdWarehouse,
} from "react-icons/md";
import { useStompClient, useSubscription } from "react-stomp-hooks";
import "./App.css";
import CardInfo from "./component/CardInfo";

import { Card, CardBody } from "@chakra-ui/react";
import ChartLine from "./component/ChartLine";

function App() {
  const stompClient = useStompClient();
  const [status, setStatus] = useState({
    totalLocomotive: 0,
    onDuty: 0,
    onDepot: 0,
    underMaintenance: 0,
    createdAt: "",
  });

  //chart
  const [dataset, setDataset] = useState([]);
  const labels = getUniqueByCreatedAt(dataset).map((item) => {
    const now = new Date(item.createdAt);

    const hours = now.getHours();
    const minutes = now.getMinutes();
    const seconds = now.getSeconds();

    return `${hours}:${minutes}:${seconds}`;
  });
  const datasets = [
    {
      label: "On Duty",
      data: getUniqueByCreatedAt(dataset).map((item) => item.onDuty),
      fill: false,
      borderColor: "#14b8a6",
      tension: 0.1,
    },
    {
      label: "On Depot",
      data: getUniqueByCreatedAt(dataset).map((item) => item.onDepot),
      fill: false,
      borderColor: "#0ea5e9",
      tension: 0.1,
    },
    {
      label: "Under Maintenance",
      data: getUniqueByCreatedAt(dataset).map((item) => item.underMaintenance),
      fill: false,
      borderColor: "rgb(255, 99, 132)",
      tension: 0.1,
    },
  ];

  //end chart

  // websocket
  // Subscribe to a topic
  useSubscription("/topic/messages", (message) => {
    const body = JSON.parse(message.body);
    setStatus(() => {
      const latest = {
        totalLocomotive: body.totalLocomotive,
        onDuty: body.status.filter((item) => item.statusId === 1)[0].total,
        onDepot: body.status.filter((item) => item.statusId === 2)[0].total,
        underMaintenance: body.status.filter((item) => item.statusId === 3)[0]
          .total,
        createdAt: body.createdAt,
      };
      setDataset((prev) => {
        console.log([...prev, latest]);
        return [...prev, latest];
      });
      return latest;
    });
  });

  useEffect(() => {
    if (stompClient) {
      stompClient.publish({
        destination: "/app/subscribe",
        body: "",
      });
    }
  }, [stompClient]);
  //end websocket
  return (
    <div className="bg-slate-50 md:px-10 p-5">
      <div className="flex-row space-y-10">
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-5">
          <div>
            <CardInfo title="Total Locomotive" value={status.totalLocomotive}>
              <MdShowChart size={30} />
            </CardInfo>
          </div>
          <div>
            <CardInfo title="On Duty" value={status.onDuty}>
              <MdTrain size={30} />
            </CardInfo>
          </div>
          <div>
            <CardInfo title="On Depot" value={status.onDepot}>
              <MdWarehouse size={30} />
            </CardInfo>
          </div>
          <div>
            <CardInfo title="Under Maintenance" value={status.underMaintenance}>
              <MdRailwayAlert size={30} />
            </CardInfo>
          </div>
        </div>
        <div className="w-full grid grid-cols-1 md:grid-cols-5 gap-10">
          <div className="md:col-span-3">
            <Card rounded={"3xl"}>
              <CardBody>
                <ChartLine
                  labels={labels}
                  datasets={datasets}
                  title={"Locomotive Satus(24h)"}
                  titleX={"Time"}
                  titleY={"Total"}
                />
              </CardBody>
            </Card>
          </div>
          <div className="">
            <div>
              <span>Locomotive Information</span>
            </div>
            <div></div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;

// Function to get unique items based on createdAt
const getUniqueByCreatedAt = (arr) => {
  const seen = new Set();
  return arr.filter((item) => {
    const date = item.createdAt;
    if (seen.has(date)) {
      return false;
    }
    seen.add(date);
    return true;
  });
};
