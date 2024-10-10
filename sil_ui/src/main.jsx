import { ChakraProvider } from "@chakra-ui/react";
import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { StompSessionProvider } from "react-stomp-hooks";
import App from "./App.jsx";
import DaashboardLayout from "./component/DashboardLayout.jsx";
import "./index.css";

createRoot(document.getElementById("root")).render(
  <StrictMode>
    <ChakraProvider>
      <StompSessionProvider url="http://192.168.0.14:8080/ws-withSock">
        <DaashboardLayout>
          <App />
        </DaashboardLayout>
      </StompSessionProvider>
    </ChakraProvider>
  </StrictMode>
);
