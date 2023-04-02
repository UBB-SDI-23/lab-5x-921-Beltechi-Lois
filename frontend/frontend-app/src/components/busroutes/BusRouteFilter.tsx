import {
    TableContainer,
    Paper,
    Table,
    TableHead,
    TableRow,
    TableCell,
    TableBody,
    CircularProgress,
    Container,
    IconButton,
    Tooltip,
    TextField,
    Button,
  } from "@mui/material";
    
  import EditIcon from "@mui/icons-material/Edit";
  import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
  import { useEffect, useState } from "react";
  import { Link, useParams } from "react-router-dom";
  import AddIcon from "@mui/icons-material/Add";
  import { BusRoute } from "../../models/BusRoute";
import { GlobalURL } from "../../main";
  
  export const BusRouteFilter = () => {
    const { givenDistance } = useParams<{ givenDistance: string }>();
    const [distance, setDistance] = useState(givenDistance ?? "");
    const [loading, setLoading] = useState(true);
    const [busroutes, setBusRoutes] = useState([]);
  
    
    // useEffect(() => {
    //   fetch(`http://localhost:8080/busroutes/higherThanGivenDistance/${distance}`)
    //     .then((res) => res.json())
    //     .then((data) => {
    //       setBusRoutes(data);
    //       setLoading(false);
    //     });
    // }, [distance]); 

    useEffect(() => {
        if (distance === '') {
          fetch(`${GlobalURL}/busroutes`)
            .then((res) => res.json())
            .then((data) => {
              setBusRoutes(data);
              setLoading(false);
            });
        } else {
          fetch(`${GlobalURL}/busroutes/higherThanGivenDistance/${distance}`)
            .then((res) => res.json())
            .then((data) => {
              setBusRoutes(data);
              setLoading(false);
            });
        }
      }, [distance]);
  
    const handleDistanceChange = (event: React.ChangeEvent<HTMLInputElement>) => {
      setDistance(event.target.value);
    };
  
    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
      event.preventDefault();
      setDistance(distance);
    };

  return (
    <Container>
        <h1>All BusRoutes filtered: &gt; given_distance </h1>
      <form onSubmit={handleSubmit}>
        <TextField
          label="Distance"
          value={distance}
          onChange={handleDistanceChange}
        />
      </form>

      {loading && <CircularProgress />}

      {!loading && busroutes.length == 0 && <div>No BusRoutes found</div>}

      {!loading && busroutes.length > 0 && (
        <TableContainer component={Paper}>
          <Table sx={{ minWidth: 800 }} aria-label="simple table">
            <TableHead>
              <TableRow>
                <TableCell>#</TableCell>
                <TableCell align="center">Bus Name</TableCell>
                <TableCell align="center">Route Type</TableCell>
                <TableCell align="center">Departure Hour</TableCell>
                <TableCell align="center">Arrival Hour</TableCell>
                <TableCell align="center">Distance</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {busroutes.map((busroutes: BusRoute, index) => (
                <TableRow key={busroutes.id}>
                  <TableCell component="th" scope="row">
                    {index + 1}
                  </TableCell>
                  <TableCell align="center">{busroutes.bus_name}</TableCell>
                  <TableCell align="center">{busroutes.route_type}</TableCell>
                  <TableCell align="center">
                    {busroutes.departure_hour}
                  </TableCell>
                  <TableCell align="center">{busroutes.arrival_hour}</TableCell>
                  <TableCell align="center">{busroutes.distance}</TableCell>

                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      )}
    </Container>
  );
};
