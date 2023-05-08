import {
    Button,
    Card,
    CardActions,
    CardContent,
    Container,
    IconButton,
    TextField,
  } from "@mui/material";
  import { useEffect, useState } from "react";
  import { Link, useNavigate, useParams } from "react-router-dom";
  import ArrowBackIcon from "@mui/icons-material/ArrowBack";
  import axios from "axios";
  import { GlobalURL } from "../../main";
  import { BACKEND_API_URL } from "../../constants";
  import { ToastContainer, toast } from "react-toastify";
  import "react-toastify/dist/ReactToastify.css";
  
  export const PersonUpdate = () => {
    const navigate = useNavigate();
  
    const { personId } = useParams();
    const [person, setPerson] = useState({
      firstName: "",
      lastName: "",
      nationality: "",
      gender: "",
      phoneNumber: "",
    });
  
    useEffect(() => {
      const fetchPerson = async () => {
        const response = await fetch(`${BACKEND_API_URL}/people/${personId}`);
        const person = await response.json();
        setPerson(person);
        console.log(person);
      };
      fetchPerson();
    }, [personId]);
  
    const updatePerson = async (event: { preventDefault: () => void }) => {
      event.preventDefault();
      try {
        const response = await axios.put(`${BACKEND_API_URL}/people/${personId}`, person);

        if (person.phoneNumber.length !== 10) {
          throw new Error("PhoneNumber = 10 digits!");
        }

        if (response.status >= 200 && response.status < 300) {
          navigate("/people");
        } else if (response.status === 400) {
          const error_message = response.data.error_message;
          toast.error(error_message);
        } else {
          throw new Error("An error occurred while adding the item!");
        }
      } catch (error) {
        if (axios.isAxiosError(error) && error.response?.status === 400) {
          const errorMessage = error.response?.data?.message;
          toast.error(errorMessage ?? "Error: fields should not be blank ||  PhoneNumber = 10 digits || Nationality [between 3,20] chars");
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
            <IconButton
              component={Link}
              sx={{ mr: 3 }}
              to={`/people/`}
            >
              <ArrowBackIcon />
            </IconButton>{" "}
            <form onSubmit={updatePerson}>
              <TextField
                id="firstName"
                label="FirstName"
                variant="outlined"
                fullWidth
                sx={{ mb: 2 }}
                onChange={(event) =>
                  setPerson({ ...person, firstName: event.target.value })
                }
                value={person.firstName}
              />
              <TextField
                id="lastName"
                label="LastName"
                variant="outlined"
                fullWidth
                sx={{ mb: 2 }}
                onChange={(event) =>
                  setPerson({ ...person, lastName: event.target.value })
                }
                value={person.lastName}
              />
  
              <TextField
                id="nationality"
                label="Nationality"
                variant="outlined"
                fullWidth
                sx={{ mb: 2 }}
                onChange={(event) =>
                    setPerson({ ...person, nationality: event.target.value })
                }
                value={person.nationality}
  
              />
  
              <TextField
                id="gender"
                label="Gender"
                variant="outlined"
                fullWidth
                sx={{ mb: 2 }}
                onChange={(event) =>
                    setPerson({ ...person, gender: event.target.value })
                }
                value={person.gender}
  
              />
  
              <TextField
                id="phoneNumber"
                label="PhoneNumber"
                variant="outlined"
                fullWidth
                sx={{ mb: 2 }}
                onChange={(event) =>
                    setPerson({ ...person, phoneNumber: event.target.value })
                }
                value={person.phoneNumber}
  
              />

              <ToastContainer />

  
              <Button type="submit">Update Person</Button>
            </form>
          </CardContent>
  
          <CardActions>
           
          </CardActions>
  
        </Card>
      </Container>
    );
  };
  