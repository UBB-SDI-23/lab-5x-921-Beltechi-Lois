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
  Box,
} from "@mui/material";

import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import { useCallback, useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import AddIcon from "@mui/icons-material/Add";
import { BusRoute } from "../../models/BusRoute";
import { GlobalURL } from "../../main";
import { BACKEND_API_URL } from "../../constants";
import axios from "axios";

export const BusRouteFilter = () => {
  const { givenDistance } = useParams<{ givenDistance: string }>();
  const [distance, setDistance] = useState(givenDistance ?? "");
  const [loading, setLoading] = useState(true);
  const [busroutes, setBusRoutes] = useState([]);
  const [page, setPage] = useState(0);
  const [totalBusRoutes, setTotalBusRoutes] = useState(0);
  const [numItems, setNumItems] = useState(0);


  const fetchBusRoutes = useCallback(async () => {
    try {
      const url = `${BACKEND_API_URL}/busroutes/higherThanGivenDistance/${distance}/page/${page}/size/100`;
      const response = await axios.get(url);
      const data = response.data;
      setBusRoutes(data);
      setNumItems(data.length);

      setLoading(false);
    } catch (error) {
      console.error(error);
      setLoading(false);
    }
  }, [distance, page]);

  useEffect(() => {
    setLoading(true);
    fetchBusRoutes();
  }, [fetchBusRoutes]);

  const pageSize = 100;

  const handlePreviousPage = () => {
    // if (page > 0) {
    //   setPage(page - 1);
    // }

    setPage(Math.max(page - 1, 0));
  };

  const handleNextPage = () => {
    setPage(page + 1);
    //setPage(Math.min(page + 1, totalPages - 1));
  };

  const startIdx = page * pageSize;
  const endIdx = Math.min(startIdx + pageSize, totalBusRoutes);

  const canNextPage = numItems === pageSize; //&& endIdx < totalBusRoutes;


  const handleDistanceChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setDistance(event.target.value);
  };

  // const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
  //   event.preventDefault();
  //   setDistance(distance);
  // };

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (distance === "") {
      setDistance("");
      return;
    }
    if (distance === givenDistance) {
      return;
    }
    setPage(0);
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

      {!loading && busroutes.length == 0 && <div>No BusRoutes found. Introduce a value </div>}
      {/* {!loading && distance === " " && <div>No Distance entered</div>} */}


      {!loading && (
        <div style={{ display: "flex", alignItems: "center" }}>
          <Button
            sx={{ color: "black" }}
            disabled={page === 0}
            onClick={handlePreviousPage}
          >
            Previous Page
          </Button>
          {/* <Button sx={{ color: "black" }} onClick={handleNextPage}>
            Next Page
          </Button> */}
          <Button
            sx={{ color: "black" }}
            onClick={handleNextPage}
            disabled={!canNextPage}
          >
            Next Page
          </Button>
        </div>
      )}

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
