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
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

  //import { debounce } from "lodash";
  
  export const LuggageAdd = () => {
    const navigate = useNavigate();
  
    const [luggage, setLuggage] = useState<Luggage>({
      id: 0,
      type: "",
      weight: 0,
      color: "",
      priority: "",
      status: "",
      description: "",
      personId:0, //??
    });
  
    const addLuggage = async (event: { preventDefault: () => void }) => {
      event.preventDefault();
      try {
        const response = await axios.post(`${BACKEND_API_URL}/luggages/people/${luggage.personId}`, luggage);
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
          toast.error(errorMessage ?? " Error: fields should not be blank ||  Weight >= 1");
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
            <IconButton component={Link} sx={{ mr: 3 }} to={`/luggages`}>
              <ArrowBackIcon />
            </IconButton>{" "}
            <form onSubmit={addLuggage}>
              <TextField
                id="type"
                label="Type"
                variant="outlined"
                fullWidth
                sx={{ mb: 2 }}
                onChange={(event) => setLuggage({ ...luggage, type: event.target.value })}
              />
              <TextField
                id="weight"
                label="Weight"
                variant="outlined"
                fullWidth
                sx={{ mb: 2 }}
                onChange={(event) =>
                  setLuggage({ ...luggage, weight: parseInt(event.target.value) })
                }
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
              />
              <Autocomplete
                id="person"
                options={people}
                getOptionLabel={(option) =>
                  `${option.firstName}, ${option.lastName}, ${option.phoneNumber}, ${option.nationality}`
                }
                renderInput={(params) => (
                  <TextField {...params} label="Person last name" variant="outlined" />
                )}
                filterOptions={(options, state) =>
                  options.filter((option) =>
                    option.lastName.toLocaleLowerCase().includes(state.inputValue.toLocaleLowerCase())
                  )
                }
                onInputChange={handleInputChange}
                onChange={(event, value) => {
                  if (value) {
                      setLuggage({ ...luggage, personId: value.id});
                  }
                }}
              />

              <ToastContainer />

  
              <Button type="submit">Add luggage</Button>
            </form>
          </CardContent>
        </Card>
      </Container>
    );
  };