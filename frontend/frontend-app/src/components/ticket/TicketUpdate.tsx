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
  
  export const TicketUpdate = () => {
    const navigate = useNavigate();
  
    const { ticketId } = useParams();
    const [ticket, setTicket] = useState({
        seat_number: "",
        payment_method: "",
      
    });
  
    useEffect(() => {
      const fetchTicket = async () => {
        const response = await fetch(`${BACKEND_API_URL}/tickets/${ticketId}`);
        const ticket = await response.json();
        setTicket(ticket);
        console.log(ticket);
      };
      fetchTicket();
    }, [ticketId]);
  
    const updateTicket = async (event: { preventDefault: () => void }) => {
      event.preventDefault();
      try {
        const response = await axios.put(`${BACKEND_API_URL}/tickets/${ticketId}`, ticket);
        
        if (response.status >= 200 && response.status < 300) {
          navigate("/tickets");
        } else if (response.status === 400) {
          const error_message = response.data.error_message;
          toast.error(error_message);
        } else {
          throw new Error("An error occurred while adding the item!");
        }
      } catch (error) {
        if (axios.isAxiosError(error) && error.response?.status === 400) {
          const errorMessage = error.response?.data?.message;
          toast.error(errorMessage ?? "PaymentMethod field should not be blank || SeatNumber between [1,3] digits");
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
              to={`/tickets/`}
            >
              <ArrowBackIcon />
            </IconButton>{" "}
            <form onSubmit={updateTicket}>
              <TextField
                id="seat_number"
                label="Seat_number"
                variant="outlined"
                fullWidth
                sx={{ mb: 2 }}
                onChange={(event) =>
                  setTicket({ ...ticket, seat_number: event.target.value })
                }
                value={ticket.seat_number}
              />
              <TextField
                id="payment_method"
                label="Payment_method"
                variant="outlined"
                fullWidth
                sx={{ mb: 2 }}
                onChange={(event) =>
                  setTicket({ ...ticket, payment_method: event.target.value })
                }
                value={ticket.payment_method}
              />


              <ToastContainer />

  
               
              <Button type="submit">Update Ticket</Button>
            </form>
          </CardContent>
  
          <CardActions>
           
          </CardActions>
  
        </Card>
      </Container>
    );
  };
  