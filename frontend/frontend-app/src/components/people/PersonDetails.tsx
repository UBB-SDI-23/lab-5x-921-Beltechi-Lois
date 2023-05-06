import { Card, CardActions, CardContent, IconButton } from "@mui/material";
import { Container } from "@mui/system";
import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import { BusRoute } from "../../models/BusRoute";
import { GlobalURL } from "../../main";
import { BACKEND_API_URL } from "../../constants";
import { Luggage } from "../../models/Luggage";
import { Person } from "../../models/Person";

export const PersonDetails = () => {
  const { personId } = useParams<{ personId: string }>();
  const [person, setPerson] = useState<Person>();

  useEffect(() => {
    const fetchPerson = async () => {
      const response = await fetch(`${BACKEND_API_URL}/people/${personId}`);
      const person = await response.json();
      setPerson(person);
      console.log(person);
    };
    fetchPerson();
  }, [personId]);

  return (
    <Container>
      <Card>
        <CardContent>
          <IconButton component={Link} sx={{ mr: 3 }} to={`/people`}>
            <ArrowBackIcon />
          </IconButton>{" "}
          <h1>Person Details</h1>
          <p>Person FirstName: {person?.firstName}</p>
          <p>Person LastName: {person?.lastName}</p>
          <p>Person nationality: {person?.nationality}</p>
          <p>Person gender: {person?.gender}</p>
          <p>Person phoneNumber: {person?.phoneNumber}</p>

          {/* <p>Person's luggages:</p>
          <ul style={{textAlign:"center", fontWeight:'bold'}}>
                        {person?.luggages?.map((lug) => (
                            <li key={lug.id}>{lug.color}, {lug.status}</li>
                        ))}
                    </ul> */}

          <p>Person's luggages:</p>
          {person && person.luggages && person.luggages.length > 0 ? (
            <ul>
              {person.luggages.map((thisluggage) => (
                <li key={thisluggage.type}>
                  {" "}
                  {thisluggage.color}, {thisluggage.status}
                </li>
              ))}
            </ul>
          ) : (
            <p>No luggages to this person yet</p>
          )}

        </CardContent>
        <CardActions>
          <IconButton
            component={Link}
            sx={{ mr: 3 }}
            to={`/people/${personId}/edit`}
          >
            <EditIcon />
          </IconButton>

          <IconButton
            component={Link}
            sx={{ mr: 3 }}
            to={`/people/${personId}/delete`}
          >
            <DeleteForeverIcon sx={{ color: "red" }} />
          </IconButton>
        </CardActions>
      </Card>
    </Container>
  );
};
