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
  Button,
  Box,
  Typography,
  TextField,
  debounce,
} from "@mui/material";
import React, { useCallback } from "react";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
//import { BACKEND_API_URL } from "../../constants";
import ReadMoreIcon from "@mui/icons-material/ReadMore";
import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import AddIcon from "@mui/icons-material/Add";
import { BusRoute } from "../../models/BusRoute";
import { GlobalURL } from "../../main";
import { BACKEND_API_URL } from "../../constants";

import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import ArrowForwardIcon from "@mui/icons-material/ArrowForward";

import {
  Row,
  Col,
  Pagination,
  PaginationItem,
  PaginationLink,
} from "reactstrap";
import axios from "axios";

export const BusRouteShowAll = () => {
  const [loading, setLoading] = useState(false);
  const [busroutes, setBusRoutes] = useState<BusRoute[]>([]);
  const [page, setPage] = useState(0);
  const [pageSize, setPageSize] = useState(100);   //Items per Page
  //const [totalBusRoutes, setTotalBusRoutes] = useState(0);

  const fetchBusRoutes = useCallback(async (page: number, pageSize: number) => {
    try {
      //const countResponse = await axios.get(`${BACKEND_API_URL}/busroutes/count`);
      //const count = countResponse.data;
  
      const dataResponse = await axios.get(`${BACKEND_API_URL}/busroutes/page/${page}/size/${pageSize}`);
      const data = dataResponse.data;
  
      //setTotalBusRoutes(count);
      setBusRoutes(data);
      setLoading(false);
    } catch (error) {
      console.error(error);
      setLoading(false);
    }
  }, []);
  
  useEffect(() => {
    console.log('useEffect triggered with page:', page, 'and pageSize:', pageSize);
    setLoading(true);
    fetchBusRoutes(page, pageSize);
  }, [fetchBusRoutes, page, pageSize]);



  const [totalBusRoutes, setTotalBusRoutes] = useState(0);
  useEffect(() => {
    axios
      .get(`${BACKEND_API_URL}/busroutes/count`)
      .then((response) => {
        setTotalBusRoutes(response.data);
      })
      .catch((error) => console.log(error));
  }, []);


  
  
  
  
  
  
  // useEffect(() => {
  //   setLoading(true);

  //   fetch(`${BACKEND_API_URL}/busroutes/page/${page}/size/${pageSize}`)
  //     .then((response) => response.json())
  //     .then((data) => {
  //       setBusRoutes(data);
  //       setLoading(false);
  //     });
  // }, [page, pageSize]);

  const sortBusRoutes = () => {
    const sortedBusRoutes = [...busroutes].sort((a: BusRoute, b: BusRoute) => {
      if (a.bus_name < b.bus_name) {
        return -1;
      }
      if (a.bus_name > b.bus_name) {
        return 1;
      }
      return 0;
    });
    setBusRoutes(sortedBusRoutes);
  };


  const totalPages = Math.max(1, Math.ceil(totalBusRoutes / pageSize));


  const handlePreviousPage = () => {
    // if (page > 0) {
    //   setPage(page - 1);
    // }

    setPage(Math.max(page - 1, 0));

  };

  const handleNextPage = () => {
    //setPage(page + 1);
    setPage(Math.min(page + 1, totalPages - 1));

  };

  const handleJump = (jump: number) => {
    setPage(Math.max(Math.min(page + jump, totalPages - 1), 0));
  };


  const startIdx = page * pageSize;
  const endIdx = Math.min(startIdx + pageSize, totalBusRoutes);

  return (
    <Container>
      <h1>All BusRoutes</h1>

      {loading && <CircularProgress />}
      {!loading && busroutes.length === 0 && <p>No busroutes found</p>}
      {!loading && (
        <div style={{ display: "flex", alignItems: "center" }}>
          <IconButton component={Link} sx={{ mr: 3 }} to={`/busroutes/add`}>
            <Tooltip title="Add a new busRoute" arrow>
              <AddIcon color="primary" />
            </Tooltip>
          </IconButton>
        </div>
      )}

      {!loading && (
        <div style={{ display: "flex", alignItems: "center" }}>
          <Button sx={{ color: "black", mr: 3 }} onClick={sortBusRoutes}>
            Sort BusRoutes
          </Button>
          <Button
            sx={{ color: "black" }}
            disabled={page === 0}
            onClick={handlePreviousPage}
          >
            Previous Page
          </Button>
          <Button sx={{ color: "black" }} onClick={handleNextPage}>
            Next Page
          </Button>

          <Box mx={2} display="flex" alignItems="center">
            Page {page + 1} of {Math.ceil(totalBusRoutes / pageSize)}
          </Box>

          <div>
          <p>
          Showing {startIdx + 1}-{endIdx} of {totalBusRoutes} busroutes
        </p>
          <button onClick={() => handleJump(-100)}>Back 100</button>
          <button onClick={() => handleJump(-10)}>Back 10</button>
          <button onClick={() => handleJump(10)}>Forward 10</button>
          <button onClick={() => handleJump(100)}>Forward 100</button>
        </div>

        

        </div>
      )}

      {!loading && busroutes.length > 0 && (
        <TableContainer component={Paper}>
          <Table sx={{ minWidth: 650 }} aria-label="simple table">
            <TableHead>
              <TableRow>
                <TableCell>#</TableCell>
                <TableCell align="right">bus_name</TableCell>
                <TableCell align="right">route_type</TableCell>
                <TableCell align="right">departure_hour</TableCell>
                <TableCell align="center">arrival_hour</TableCell>
                <TableCell align="center">distance</TableCell>
                <TableCell align="center">Options</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {Array.isArray(busroutes) && busroutes.map((busroute, index) => (
                <TableRow key={busroute.id}>
                  <TableCell component="th" scope="row">
                    {index + 1}
                  </TableCell>
                  <TableCell component="th" scope="row">
                    <Link
                      to={`/busroutes/${busroute.id}/details`}
                      title="View busRoute details"
                    >
                      {busroute.bus_name}
                    </Link>
                  </TableCell>

                  <TableCell align="right">{busroute.route_type}</TableCell>
                  <TableCell align="right">{busroute.departure_hour}</TableCell>
                  <TableCell align="right">{busroute.arrival_hour}</TableCell>
                  <TableCell align="center">{busroute.distance}</TableCell>

                  <TableCell align="right">
                    <IconButton
                      component={Link}
                      sx={{ mr: 3 }}
                      to={`/busroutes/${busroute.id}/details`}
                    >
                      <Tooltip title="View busRoute details" arrow>
                        <ReadMoreIcon color="primary" />
                      </Tooltip>
                    </IconButton>

                    <IconButton
                      component={Link}
                      sx={{ mr: 3 }}
                      to={`/busroutes/${busroute.id}/edit`}
                      title="Edit busRoute"
                    >
                      <EditIcon />
                    </IconButton>

                    <IconButton
                      component={Link}
                      sx={{ mr: 3 }}
                      to={`/busroutes/${busroute.id}/delete`}
                      title="Delete busRoute"
                    >
                      <DeleteForeverIcon sx={{ color: "red" }} />
                    </IconButton>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      )}

      <Button
        sx={{ color: "black" }}
        disabled={page === 0}
        onClick={handlePreviousPage}
      >
        Previous Page
      </Button>
      <Button sx={{ color: "black" }} onClick={handleNextPage}>
        Next Page
      </Button>
    </Container>
  );
};
