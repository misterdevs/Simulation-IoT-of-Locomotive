import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { ChakraProvider } from "@chakra-ui/react";
import App from "./App.jsx";
import "./index.css";
import { StompSessionProvider } from "react-stomp-hooks";
import { MdPerson, MdTrain } from "react-icons/md";

createRoot(document.getElementById("root")).render(
  <StrictMode>
    <ChakraProvider>
      <StompSessionProvider url="http://192.168.0.14:8080/ws-withSock">
        <div className="w-full h-full">
          <div className="flex h-14 w-full justify-between items-center px-5 bg-gradient-to-r from-cyan-500 to-blue-500">
            <div className="flex space-x-2 items-center">
              <div>
                <MdTrain size={30} color="white" />
              </div>
              <div className="text-white">
                <span className="font-bold">ILOCOS</span> |
                <span className="text-sm"> IoT Locomotive System</span>
              </div>
            </div>
            <div>
              <div className="flex justify-center items-center p-1 rounded-full bg-white">
                <MdPerson size={20} className=" text-blue-500 " />
              </div>
            </div>
          </div>
          <App />
        </div>
      </StompSessionProvider>
    </ChakraProvider>
  </StrictMode>
);
