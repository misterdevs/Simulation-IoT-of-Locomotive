import { MdPerson, MdTrain } from "react-icons/md";

const DaashboardLayout = (props) => {
  return (
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
      {props.children}
    </div>
  );
};

export default DaashboardLayout;
