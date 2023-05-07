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
import ReadMoreIcon from "@mui/icons-material/ReadMore";
import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import AddIcon from "@mui/icons-material/Add";
import { BACKEND_API_URL } from "../../constants";
import axios from "axios";
import { Person } from "../../models/Person";

import "../../assets/css/pagination.css";

export const PersonShowAll = () => {
  const [loading, setLoading] = useState(false);
  const [people, setPeople] = useState<Person[]>([]);
  const [page, setPage] = useState(0);
  const [pageSize, setPageSize] = useState(15); //Items per Page

  const fetchBusRoutes = useCallback(async (page: number, pageSize: number) => {
    try {
      const dataResponse = await axios.get(
        `${BACKEND_API_URL}/people/page/${page}/size/${pageSize}`
      );
      const data = dataResponse.data;

      setPeople(data);
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
    fetchBusRoutes(page, pageSize);
  }, [fetchBusRoutes, page, pageSize]);

  const [totalPeople, setTotalPeople] = useState(0);
  useEffect(() => {
    axios
      .get(`${BACKEND_API_URL}/people/count`)
      .then((response) => {
        setTotalPeople(response.data);
      })
      .catch((error) => console.log(error));
  }, []);

  const totalPages = Math.max(1, Math.ceil(totalPeople / pageSize));

  const startIdx = page * pageSize;
  const endIdx = Math.min(startIdx + pageSize, totalPeople);

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
      <h1>All People</h1>

      {loading && <CircularProgress />}
      {!loading && people.length === 0 && <p>No people found</p>}
      {!loading && (
        <div style={{ display: "flex", alignItems: "center" }}>
          <IconButton component={Link} sx={{ mr: 3 }} to={`/people/add`}>
            <Tooltip title="Add a new person" arrow>
              <AddIcon color="primary" />
            </Tooltip>
          </IconButton>
        </div>
      )}

      {!loading && (
        <div style={{ display: "flex", alignItems: "center" }}>
          {/* <div>
            Showing {startIdx + 1}-{endIdx} of {totalPeople} people
          </div> */}

<div className="pagination">
            
            {getPageNumbers().map((pageNumber, index) => (
              <button
                key={index}
                className={`page-numbers ${
                  pageNumber === page ? "active" : ""
                }`}
                // className={`btn me-2 ${
                //   pageNumber === page ? "btn-primary" : "btn-secondary"
                // }`}
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

      {!loading && people.length > 0 && (
        <TableContainer component={Paper}>
          <Table sx={{ minWidth: 650 }} aria-label="simple table">
            <TableHead>
              <TableRow>
                <TableCell>#</TableCell>
                <TableCell align="right">Last Name</TableCell>
                <TableCell align="right">First Name</TableCell>
                <TableCell align="right">Nationality</TableCell>
                <TableCell align="center">Gender</TableCell>
                <TableCell align="center">PhoneNumber</TableCell>
                <TableCell align="center">noOfLuggages</TableCell>
                <TableCell align="center">Options</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {Array.isArray(people) &&
                people.map((person, index) => (
                  <TableRow key={person.id}>
                    <TableCell component="th" scope="row">
                      {index + 1}
                    </TableCell>
                    <TableCell component="th" scope="row">
                      <Link
                        to={`/people/${person.id}/details`}
                        title="View person details"
                      >
                        {person.lastName}
                      </Link>
                    </TableCell>

                    {/* <TableCell align="center">{person.lastName}</TableCell> */}
                    <TableCell align="center">{person.firstName}</TableCell>
                    <TableCell align="center">{person.nationality}</TableCell>
                    <TableCell align="center">{person.gender}</TableCell>
                    <TableCell align="center">{person.phoneNumber}</TableCell>
                    <TableCell align="center">{person.noOfLuggages}</TableCell>

                    <TableCell align="right">
                      <IconButton
                        component={Link}
                        sx={{ mr: 3 }}
                        to={`/people/${person.id}/details`}
                      >
                        <Tooltip title="View person details" arrow>
                          <ReadMoreIcon color="primary" />
                        </Tooltip>
                      </IconButton>

                      <IconButton
                        component={Link}
                        sx={{ mr: 3 }}
                        to={`/people/${person.id}/edit`}
                        title="Edit person"
                      >
                        <EditIcon />
                      </IconButton>

                      <IconButton
                        component={Link}
                        sx={{ mr: 3 }}
                        to={`/people/${person.id}/delete`}
                        title="Delete person"
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
