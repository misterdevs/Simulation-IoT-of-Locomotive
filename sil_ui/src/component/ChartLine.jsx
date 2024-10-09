import {
  CategoryScale,
  Chart as ChartJS,
  Legend,
  LinearScale,
  LineElement,
  PointElement,
  Title,
  Tooltip,
} from "chart.js";
import { Line } from "react-chartjs-2";
import zoomPlugin from "chartjs-plugin-zoom";

// Register Chart.js components
ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
  zoomPlugin
);

const ChartLine = ({ title, titleX, titleY, labels, datasets }) => {
  const data = {
    labels: labels,
    datasets: datasets,
  };

  // Options for the chart
  const options = {
    responsive: true,
    scales: {
      x: {
        title: {
          display: true,
          text: titleX,
        },
      },
      y: {
        beginAtZero: true,
        title: {
          display: true,
          text: titleY,
        },
      },
    },
    plugins: {
      title: {
        display: true,
        text: title,
      },
      zoom: {
        pan: {
          enabled: true,
          mode: "xy",
        },
        zoom: {
          wheel: {
            enabled: true,
          },
          pinch: {
            enabled: true,
          },
          mode: "xy",
        },
      },
    },
  };

  return (
    <>
      <div className="md:hidden">
        <Line data={data} options={options} height={400} />
      </div>
      <div className="hidden md:block">
        <Line data={data} options={options} height={200} />
      </div>
    </>
  );
};

export default ChartLine;
