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
import { Luggage } from "../../models/Luggage";

export const LuggageShowAll = () => {
  const [loading, setLoading] = useState(false);
  const [luggages, setLuggages] = useState<Luggage[]>([]);
  const [page, setPage] = useState(0);
  const [pageSize, setPageSize] = useState(15); //Items per Page

  const fetchLuggages = useCallback(async (page: number, pageSize: number) => {
    try {
      const dataResponse = await axios.get(
        `${BACKEND_API_URL}/luggages/page/${page}/size/${pageSize}`
      );
      const data = dataResponse.data;

      setLuggages(data);
      setLoading(false);
    } catch (error) {
      console.error(error);
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    console.log(
      "useEffect triggered with page:",
      page,
      "and pageSize:",
      pageSize
    );
    setLoading(true);
    fetchLuggages(page, pageSize);
  }, [fetchLuggages, page, pageSize]);

  const [totalLuggages, setTotalLuggages] = useState(0);
  useEffect(() => {
    axios
      .get(`${BACKEND_API_URL}/luggages/count`)
      .then((response) => {
        setTotalLuggages(response.data);
      })
      .catch((error) => console.log(error));
  }, []);

  const totalPages = Math.max(1, Math.ceil(totalLuggages / pageSize));

  
  const startIdx = page * pageSize;
  const endIdx = Math.min(startIdx + pageSize, totalLuggages);

  const getPageNumbers = () => {
    const pageNumbers = [];
    let i;
    for (i = 0; i < totalPages; i++) {
      if (
        i === 0 ||
        i === totalPages - 1 ||
        (i >= page - 5 && i <= page + 5) ||
        i < 5 ||
        i > totalPages - 6
      ) {
        pageNumbers.push(i);
      } else if (
        pageNumbers[pageNumbers.length - 1] !== "..." &&
        (i < page - 5 || i > page + 5)
      ) {
        pageNumbers.push("...");
      }
    }
    return pageNumbers;
  };

  const handlePageClick = (pageNumber: number) => {
    setPage(pageNumber);
  };

  return (
    <Container>
      <h1>All Luggages</h1>

      {loading && <CircularProgress />}
      {!loading && luggages.length === 0 && <p>No luggages found</p>}
      {!loading && (
        <div style={{ display: "flex", alignItems: "center" }}>
          <IconButton component={Link} sx={{ mr: 3 }} to={`/luggages/add`}>
            <Tooltip title="Add a new luggage" arrow>
              <AddIcon color="primary" />
            </Tooltip>
          </IconButton>
        </div>
      )}

      {!loading && (
        <div style={{ display: "flex", alignItems: "center" }}>


          {/* <div>
            Showing {startIdx + 1}-{endIdx} of {totalLuggages} luggages
          </div> */}

          <div>
            
            {getPageNumbers().map((pageNumber, index) => (
              <button
                key={index}
                className={`btn me-2 ${
                  pageNumber === page ? "btn-primary" : "btn-secondary"
                }`}
                onClick={() => handlePageClick(Number(pageNumber))}
                disabled={pageNumber === "..." || pageNumber === page}
              >
                {pageNumber === "..."
                  ? "..."
                  : typeof pageNumber === "number"
                  ? pageNumber + 1
                  : ""}
              </button>
            ))}
            
          </div>
        </div>
      )}

      {!loading && luggages.length > 0 && (
        <TableContainer component={Paper}>
          <Table sx={{ minWidth: 650 }} aria-label="simple table">
            <TableHead>
              <TableRow>
                <TableCell>#</TableCell>
                <TableCell align="right">Luggage Type</TableCell>
                <TableCell align="right">Luggage Weight</TableCell>
                <TableCell align="right">Luggage Color</TableCell>
                <TableCell align="right">Luggage Priority</TableCell>
                <TableCell align="right">Luggage Status</TableCell>
                <TableCell align="center">Luggage Description</TableCell>
                <TableCell align="center">Options</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {Array.isArray(luggages) &&
                luggages.map((luggage, index) => (
                  <TableRow key={luggage.id}>
                    <TableCell component="th" scope="row">
                      {index + 1}
                    </TableCell>
                    <TableCell component="th" scope="row">
                      <Link
                        to={`/luggages/${luggage.id}/details`}
                        title="View luggage details"
                      >
                        {luggage.type}
                      </Link>
                    </TableCell>
                    <TableCell align="center">{luggage.weight}</TableCell>
                    <TableCell align="center">{luggage.color}</TableCell>
                    <TableCell align="center">{luggage.priority}</TableCell>
                    <TableCell align="center">{luggage.status}</TableCell>
                    <TableCell align="center">{luggage.description}</TableCell>

                    <TableCell align="right">
                      <IconButton
                        component={Link}
                        sx={{ mr: 3 }}
                        to={`/luggages/${luggage.id}/details`}
                      >
                        <Tooltip title="View luggage details" arrow>
                          <ReadMoreIcon color="primary" />
                        </Tooltip>
                      </IconButton>

                      <IconButton
                        component={Link}
                        sx={{ mr: 3 }}
                        to={`/luggages/${luggage.id}/edit`}
                        title="Edit luggage"
                      >
                        <EditIcon />
                      </IconButton>

                      <IconButton
                        component={Link}
                        sx={{ mr: 3 }}
                        to={`/luggages/${luggage.id}/delete`}
                        title="Delete luggage"
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
    </Container>
  );
};
