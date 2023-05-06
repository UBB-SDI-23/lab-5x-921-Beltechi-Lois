import {
  Autocomplete,
  Button,
  Card,
  CardActions,
  CardContent,
  IconButton,
  TextField,
} from "@mui/material";
import { Container } from "@mui/system";
import { useCallback, useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import axios from "axios";
import { GlobalURL } from "../../main";
import debounce from "lodash/debounce";
import { Luggage } from "../../models/Luggage";
import { BACKEND_API_URL } from "../../constants";
import { Person } from "../../models/Person";
import { Ticket } from "../../models/Ticket";
import { BusRoute } from "../../models/BusRoute";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

//import { debounce } from "lodash";

export const TicketAdd = () => {
  const navigate = useNavigate();

  const [ticket, setTicket] = useState<Ticket>({
    id: 0,
    payment_method: "",
    seat_number: "",
    personId: 0,
    bus_routeId: 0,
  });

  // const addTicket = async (event: { preventDefault: () => void }) => {
  //   event.preventDefault();
  //   try {
  //     //await axios.post(`${BACKEND_API_URL}/tickets/people/${ticket.personId}/busroutes/${ticket.bus_routeId}`,ticket);
  //     const response = await axios.post(`${BACKEND_API_URL}/tickets/people/${ticket.personId}/busroutes/${ticket.bus_routeId}`, ticket);
  //     if (response.status < 200 || response.status <= 300) {
	// 			throw new Error("An error occurred while adding the item!");
	// 		  }
  //       else if (response.status===400) {
  //         throw new Error("damada");
  //         }

        
  //       else {
  //         navigate("/tickets");
	// 		  }
  //   } catch (error) {
  //     toast.error((error as { message: string }).message);
  //     console.log(error);
  //   }
  // };

 

  const addTicket = async (event: { preventDefault: () => void }) => {
    event.preventDefault();
    try {
      const response = await axios.post(`${BACKEND_API_URL}/tickets/people/${ticket.personId}/busroutes/${ticket.bus_routeId}`, ticket);
      if (response.status >= 200 && response.status < 300) {
        navigate("/tickets");
      } else if (response.status === 400) {
        const error_message = response.data.error_message;
        toast.error(error_message);
      } else {
        throw new Error("An error occurred while adding the item!");
      }
    } catch (error) {
      if (axios.isAxiosError(error) && error.response?.status === 400) {
        const errorMessage = error.response?.data?.message;
        toast.error(errorMessage ?? "Error: PaymentMethod field should not be blank || SeatNumber exactly 3 digits");
      } else {
        toast.error("An error occurred while adding the item!");
      }
      console.log(error);
    }
  };

  const [people, setPeople] = useState<Person[]>([]);
  const [busroutes, setBusRoutes] = useState<BusRoute[]>([]);

  const fetchPeopleSuggestions = async (query: string) => {
    try {
      let url = BACKEND_API_URL + `/people/autocomplete?query=${query}`;

      const response = await fetch(url);

      const data = await response.json();

      setPeople(data);

      console.log(data);
    } catch (error) {
      console.log("Error fetching suggestions:", error);
    }
  };

  const fetchBusRoutesSuggestions = async (query: string) => {
    try {
      let url = BACKEND_API_URL + `/busroutes/autocomplete?query=${query}`;

      const response = await fetch(url);

      const data = await response.json();

      setBusRoutes(data);

      console.log(data);
    } catch (error) {
      console.log("Error fetching suggestions:", error);
    }
  };

  const debouncedFetchPeopleSuggestions = useCallback(
    debounce(fetchPeopleSuggestions, 500),
    []
  );

  const debouncedFetchBusRoutesSuggestions = useCallback(
    debounce(fetchBusRoutesSuggestions, 500),
    []
  );

  const handlePeopleInputChange = (event: any, value: any, reason: any) => {
    console.log("input", value, reason);

    if (reason == "input") {
      debouncedFetchPeopleSuggestions(value);
    }
  };

  const handleBusRoutesInputChange = (event: any, value: any, reason: any) => {
    console.log("input", value, reason);

    if (reason == "input") {
      debouncedFetchBusRoutesSuggestions(value);
    }
  };

  useEffect(() => {
    return () => {
      debouncedFetchPeopleSuggestions.cancel();
      debouncedFetchBusRoutesSuggestions.cancel();
    };
  }, [debouncedFetchPeopleSuggestions, debouncedFetchBusRoutesSuggestions]);

  return (
    <Container>
      <Card>
        <CardContent>
          <IconButton component={Link} sx={{ mr: 3 }} to={`/tickets`}>
            <ArrowBackIcon />
          </IconButton>{" "}
          <form onSubmit={addTicket}>
            <TextField
              id="payment_method"
              label="Payment method"
              variant="outlined"
              fullWidth
              sx={{ mb: 2 }}
              onChange={(event) =>
                setTicket({ ...ticket, payment_method: event.target.value })
              }
            />
            <TextField
              id="seat_number"
              label="Seat number"
              variant="outlined"
              fullWidth
              sx={{ mb: 2 }}
              onChange={(event) =>
                setTicket({ ...ticket, seat_number: event.target.value })
              }
            />

            <Autocomplete
              id="person"
              options={people}
              getOptionLabel={(option) =>
                `${option.firstName}, ${option.lastName}, ${option.phoneNumber}, ${option.nationality}`
              }
              renderInput={(params) => (
                <TextField
                  {...params}
                  label="Person last name"
                  variant="outlined"
                />
              )}
              filterOptions={(options, state) =>
                options.filter((option) =>
                  option.lastName
                    .toLocaleLowerCase()
                    .includes(state.inputValue.toLocaleLowerCase())
                )
              }
              onInputChange={handlePeopleInputChange}
              onChange={(event, value) => {
                if (value) {
                  setTicket({ ...ticket, personId: value.id });
                }
              }}
            />

            <div>
              <br></br>
            </div>

            <Autocomplete
              id="busRoute"
              options={busroutes}
              getOptionLabel={(option) =>
                `${option.bus_name}  ${option.distance}, ${option.route_type}`
              }
              renderInput={(params) => (
                <TextField
                  {...params}
                  label="BusRoute bus name"
                  variant="outlined"
                />
              )}
              filterOptions={(options, state) =>
                options.filter((option) =>
                  option.bus_name
                    .toLocaleLowerCase()
                    .includes(state.inputValue.toLocaleLowerCase())
                )
              }
              onInputChange={handleBusRoutesInputChange}
              onChange={(event, value) => {
                if (value) {
                  setTicket({
                    ...ticket,
                    bus_routeId: value.id,
                  });
                }
              }}
            />

            <ToastContainer />


            <Button type="submit">Add a Ticket</Button>
          </form>
        </CardContent>
      </Card>
    </Container>
  );
};
