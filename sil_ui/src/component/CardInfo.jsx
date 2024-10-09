import { Card, CardBody } from "@chakra-ui/react";

const CardInfo = (props) => {
  return (
    <Card rounded={"3xl"}>
      <CardBody className="w-full ">
        <div className="flex justify-between items-center w-full px-2">
          <div className="flex-row">
            <div className="text-sm">{props.title}</div>
            <div className="text-2xl font-bold">{props.value}</div>
          </div>
          <div>{props.children}</div>
        </div>
      </CardBody>
    </Card>
  );
};

export default CardInfo;
