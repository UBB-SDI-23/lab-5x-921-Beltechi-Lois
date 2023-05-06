import {
  Button,
  Card,
  CardActions,
  CardContent,
  Container,
  IconButton,
  TextField,
} from "@mui/material";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import axios from "axios";
import { GlobalURL } from "../../main";
import { BACKEND_API_URL } from "../../constants";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

export const BusRouteAdd = () => {
  const navigate = useNavigate();

  const [busroute, setBusRoute] = useState({
    bus_name: "",
    route_type: "",
    departure_hour: "",
    arrival_hour: "",
    distance: "",
  });

  const addBusRoute = async (event: { preventDefault: () => void }) => {
    event.preventDefault();

    try {
      const response = await axios.post(
        `${BACKEND_API_URL}/busroutes`,
        busroute
      );

      const isValidHourFormat = /^(?:[01]\d|2[0-3]):[0-5]\d$/.test(
        busroute.departure_hour
      );

      const isValidHourFormat2 = /^(?:[01]\d|2[0-3]):[0-5]\d$/.test(
        busroute.arrival_hour
      );

      if (!isValidHourFormat || !isValidHourFormat2) {
        throw new Error("Hours are HH:MM");
      } else {
        if (response.status >= 200 && response.status < 300) {
          navigate("/busroutes");
        } else if (response.status === 400) {
          const error_message = response.data.error_message;
          toast.error(error_message);
        } else {
          throw new Error("An error occurred while adding the item!");
        }
      }
    } catch (error) {
      if (axios.isAxiosError(error) && error.response?.status === 400) {
        const errorMessage = error.response?.data?.message;
        toast.error(
          errorMessage ?? "Fields should not be blank || Hours: HH:MM"
        );
      } else {
        toast.error("An error occurred while adding the item!");
      }
      console.log(error);
    }
  };

  return (
    <Container>
      <Card>
        <CardContent>
          <IconButton component={Link} sx={{ mr: 3 }} to={`/busroutes`}>
            <ArrowBackIcon />
          </IconButton>{" "}
          <form onSubmit={addBusRoute}>
            <TextField
              id="bus_name"
              label="Name"
              variant="outlined"
              fullWidth
              sx={{ mb: 2 }}
              onChange={(event) =>
                setBusRoute({ ...busroute, bus_name: event.target.value })
              }
            />
            <TextField
              id="route_type"
              label="Type"
              variant="outlined"
              fullWidth
              sx={{ mb: 2 }}
              onChange={(event) =>
                setBusRoute({ ...busroute, route_type: event.target.value })
              }
            />

            <TextField
              id="departure_hour"
              label="Dept_Hour"
              variant="outlined"
              fullWidth
              sx={{ mb: 2 }}
              onChange={(event) =>
                setBusRoute({ ...busroute, departure_hour: event.target.value })
              }
            />

            <TextField
              id="arrival_hour"
              label="Arr_Hour"
              variant="outlined"
              fullWidth
              sx={{ mb: 2 }}
              onChange={(event) =>
                setBusRoute({ ...busroute, arrival_hour: event.target.value })
              }
            />

            <TextField
              id="distance"
              label="KM"
              variant="outlined"
              fullWidth
              sx={{ mb: 2 }}
              onChange={(event) =>
                setBusRoute({ ...busroute, distance: event.target.value })
              }
            />

            <ToastContainer />

            <Button type="submit">Add BusRoute</Button>
          </form>
        </CardContent>
        <CardActions></CardActions>
      </Card>
    </Container>
  );
};
