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
import { Ticket } from "../../models/Ticket";

export const TicketShowAll = () => {
  const [loading, setLoading] = useState(false);
  const [tickets, setTickets] = useState<Ticket[]>([]);
  const [page, setPage] = useState(0);
  const [pageSize, setPageSize] = useState(15); //Items per Page

  const fetchTickets = useCallback(async (page: number, pageSize: number) => {
    try {
      const dataResponse = await axios.get(
        `${BACKEND_API_URL}/tickets/page/${page}/size/${pageSize}`
      );
      const data = dataResponse.data;

      setTickets(data);
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
    fetchTickets(page, pageSize);
  }, [fetchTickets, page, pageSize]);

  const [totalTickets, setTotalTickets] = useState(0);
  useEffect(() => {
    axios
      .get(`${BACKEND_API_URL}/tickets/count`)
      .then((response) => {
        setTotalTickets(response.data);
      })
      .catch((error) => console.log(error));
  }, []);

  const totalPages = Math.max(1, Math.ceil(totalTickets / pageSize));

  const startIdx = page * pageSize;
  const endIdx = Math.min(startIdx + pageSize, totalTickets);

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
      <h1>All Tickets</h1>

      {loading && <CircularProgress />}
      {!loading && tickets.length === 0 && <p>No tickets found</p>}
      {!loading && (
        <div style={{ display: "flex", alignItems: "center" }}>
          <IconButton component={Link} sx={{ mr: 3 }} to={`/tickets/add`}>
            <Tooltip title="Add a new ticket" arrow>
              <AddIcon color="primary" />
            </Tooltip>
          </IconButton>
        </div>
      )}

      {!loading && (
        <div style={{ display: "flex", alignItems: "center" }}>
          
          {/* <div>
            Showing {startIdx + 1}-{endIdx} of {totalTickets} tickets
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

      {!loading && tickets.length > 0 && (
        <TableContainer component={Paper}>
          <Table sx={{ minWidth: 650 }} aria-label="simple table">
            <TableHead>
              <TableRow>
                <TableCell>#</TableCell>
                <TableCell align="left">Ticket Payment Method</TableCell>
                <TableCell align="center">Ticket Seat Number</TableCell>
                <TableCell align="center">Options</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {Array.isArray(tickets) &&
                tickets.map((ticket, index) => (
                  <TableRow key={ticket.id}>
                    <TableCell component="th" scope="row">
                      {index + 1}
                    </TableCell>
                    <TableCell component="th" scope="row">
                      <Link
                        to={`/tickets/${ticket.id}/details`}
                        title="View ticket details"
                      >
                        {ticket.payment_method}
                      </Link>
                    </TableCell>
                    {/* <TableCell align="center">{ticket.payment_method}</TableCell> */}
                    <TableCell align="center">{ticket.seat_number}</TableCell>

                    <TableCell align="right">
                      <IconButton
                        component={Link}
                        sx={{ mr: 3 }}
                        to={`/tickets/${ticket.id}/details`}
                      >
                        <Tooltip title="View ticket details" arrow>
                          <ReadMoreIcon color="primary" />
                        </Tooltip>
                      </IconButton>

                      <IconButton
                        component={Link}
                        sx={{ mr: 3 }}
                        to={`/tickets/${ticket.id}/edit`}
                        title="Edit ticket"
                      >
                        <EditIcon />
                      </IconButton>

                      <IconButton
                        component={Link}
                        sx={{ mr: 3 }}
                        to={`/tickets/${ticket.id}/delete`}
                        title="Delete ticket"
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
