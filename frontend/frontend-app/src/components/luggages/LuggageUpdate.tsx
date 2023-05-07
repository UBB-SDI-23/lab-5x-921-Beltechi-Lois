import {
  Autocomplete,
  Button,
  Card,
  CardActions,
  CardContent,
  Container,
  IconButton,
  TextField,
} from "@mui/material";
import { useCallback, useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import axios from "axios";
import { GlobalURL } from "../../main";
import { BACKEND_API_URL } from "../../constants";
import { Person } from "../../models/Person";
import debounce from "lodash/debounce";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

export const LuggageUpdate = () => {
  const navigate = useNavigate();

  const { luggageId } = useParams();

  const [luggage, setLuggage] = useState({
    type: "",
    weight: 1,
    color: "",
    priority: "",
    status: "",
    description: "",
    personId: 0, 
  });


  //const [personna, setPerson] = useState<Person>();

  useEffect(() => {
    const fetchLuggage = async () => {
      const response = await fetch( `${BACKEND_API_URL}/luggages/${luggageId}`);
      const luggage = await response.json();
      setLuggage(luggage);

      
      //const personidget= luggage.personId;
      //console.log(personidget);
      //const personresponse=await fetch( `${BACKEND_API_URL}/people/${personidget}`);

      //const personna = await personresponse.json();
      //setPerson(personna);

      //console.log(personna);

      
    //   setLuggage({
		// 		type: luggage.type,
    //     weight: luggage.weight,
    //             color: luggage.color,
    //             priority: luggage.priority,
    //             status: luggage.status,
    //             description: luggage.description,
    //             personId: luggage.personId,
		// })

      console.log(luggage);
    };
    fetchLuggage();
  }, [luggageId]);


  const updateLuggage = async (event: { preventDefault: () => void }) => {
    event.preventDefault();
    try {
      const response=await axios.put(
        `${BACKEND_API_URL}/luggages/${luggageId}/person/${luggage.personId}`,
        luggage
      );
      if (response.status >= 200 && response.status < 300) {
        navigate("/luggages");
      } else if (response.status === 400) {
        const error_message = response.data.error_message;
        toast.error(error_message);
      } else {
        throw new Error("An error occurred while adding the item!");
      }
    } catch (error) {
      if (axios.isAxiosError(error) && error.response?.status === 400) {
        const errorMessage = error.response?.data?.message;
        toast.error(errorMessage ?? "Error: fields should not be blank ||  Weight >= 1");
      } else {
        toast.error("An error occurred while adding the item!");
      }
      console.log(error);
    }
  };

  const [people, setPeople] = useState<Person[]>([]);

  const fetchSuggestions = async (query: string) => {
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

  const debouncedFetchSuggestions = useCallback(
    debounce(fetchSuggestions, 500),
    []
  );

  const handleInputChange = (event: any, value: any, reason: any) => {
    console.log("input", value, reason);

    if (reason == "input") {
      debouncedFetchSuggestions(value);
    }
  };

  useEffect(() => {
    return () => {
      debouncedFetchSuggestions.cancel();
    };
  }, [debouncedFetchSuggestions]);

  return (
    <Container>
      <Card>
        <CardContent>
          <IconButton component={Link} sx={{ mr: 3 }} to={`/luggages/`}>
            <ArrowBackIcon />
          </IconButton>{" "}
          <form onSubmit={updateLuggage}>
            <TextField
              id="type"
              label="Type"
              variant="outlined"
              fullWidth
              sx={{ mb: 2 }}
              onChange={(event) =>
                setLuggage({ ...luggage, type: event.target.value })
              }
              value={luggage.type}
            />
            <TextField
              id="weight"
              label="Weight"
              variant="outlined"
              fullWidth
              sx={{ mb: 2 }}
              onChange={(event) =>
                setLuggage({ ...luggage, weight: parseInt(event.target.value) || 0})
              }
              value={luggage.weight}
            />

            <TextField
              id="color"
              label="Color"
              variant="outlined"
              fullWidth
              sx={{ mb: 2 }}
              onChange={(event) =>
                setLuggage({ ...luggage, color: event.target.value })
              }
              value={luggage.color}
            />

            <TextField
              id="priority"
              label="Priority"
              variant="outlined"
              fullWidth
              sx={{ mb: 2 }}
              onChange={(event) =>
                setLuggage({ ...luggage, priority: event.target.value })
              }
              value={luggage.priority}
            />

            <TextField
              id="status"
              label="Status"
              variant="outlined"
              fullWidth
              sx={{ mb: 2 }}
              onChange={(event) =>
                setLuggage({ ...luggage, status: event.target.value })
              }
              value={luggage.status}
            />

            <TextField
              id="description"
              label="Description"
              variant="outlined"
              fullWidth
              sx={{ mb: 2 }}
              onChange={(event) =>
                setLuggage({ ...luggage, description: event.target.value })
              }
              value={luggage.description}
            />

            
            <Autocomplete
              id="person"
              //value={personna}
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
              onInputChange={handleInputChange}
              onChange={(event, value) => {
                if (value) {
                  setLuggage({ ...luggage, personId: value.id });
                  
                }
                
              }}
              //value={people.find((person) => person.id === luggage.personId)}
              //value={people.find((person) => person.id === luggage.personId) ?? {}}
              //value={people.find((person) => person.id.toString() === luggage.personId.toString())}

              //value={personna?.lastName}
              //value={personna?.lastName ?? null}


              

            />

            <ToastContainer />


            <Button type="submit">Update Luggage</Button>
          </form>
        </CardContent>

        <CardActions></CardActions>
      </Card>
    </Container>
  );
};
