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

export const PersonAdd = () => {
  const navigate = useNavigate();

  const [person, setPerson] = useState({
    firstName: "",
    lastName: "",
    nationality: "",
    gender: "",
    phoneNumber: "",
  });



  const addPerson = async (event: { preventDefault: () => void }) => {
    event.preventDefault();
    try {
      const response = await axios.post(`${BACKEND_API_URL}/people`, person);

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
        toast.error(errorMessage ?? " Error: fields should not be blank ||  PhoneNumber = 10 digits");
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
          <IconButton component={Link} sx={{ mr: 3 }} to={`/people`}>
            <ArrowBackIcon />
          </IconButton>{" "}
          <form onSubmit={addPerson}>
            <TextField
              id="firstName"
              label="First Name"
              variant="outlined"
              fullWidth
              sx={{ mb: 2 }}
              onChange={(event) => setPerson({ ...person, firstName: event.target.value })}
            />
            <TextField
              id="lastName"
              label="Last name"
              variant="outlined"
              fullWidth
              sx={{ mb: 2 }}
              onChange={(event) => setPerson({ ...person, lastName: event.target.value })}
            />

            <TextField
              id="nationality"
              label="Nationality"
              variant="outlined"
              fullWidth
              sx={{ mb: 2 }}
              onChange={(event) => setPerson({ ...person, nationality: event.target.value })}
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
            />

            <TextField
              id="phoneNumber"
              label="PhoneNumber"
              variant="outlined"
              fullWidth
              sx={{ mb: 2 }}
              onChange={(event) => {
                setPerson({ ...person, phoneNumber: event.target.value });
              }}
            />

            <ToastContainer />


            <Button type="submit">Add Person</Button>
          </form>
        </CardContent>
        <CardActions></CardActions>
      </Card>
    </Container>
  );
};
