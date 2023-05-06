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
import { Ticket } from "../../models/Ticket";

export const TicketDetails = () => {
    const { ticketId } = useParams<{ ticketId: string }>();
	const [ticket, setTicket] = useState<Ticket>();

	useEffect(() => {
		const fetchTicket = async () => {
		

			const response = await fetch(`${BACKEND_API_URL}/tickets/${ticketId}`);
			const ticket = await response.json();
			setTicket(ticket);
            console.log(ticket);
		};
		fetchTicket();
	}, [ticketId]);

	return (
		<Container>
			<Card>
				<CardContent>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/tickets`}>
						<ArrowBackIcon />
					</IconButton>{" "}
					<h1>Ticket Details</h1>
					<p>Ticket SeatNumber: {ticket?.seat_number}</p>
					<p>Ticket PaymentMethod: {ticket?.payment_method}</p>
                    <p>Ticket person assigned : {ticket?.person?.firstName}  {ticket?.person?.lastName}</p>
                    <p>Ticket busRoute assigned : {ticket?.bus_route?.bus_name}  {ticket?.bus_route?.distance}</p>




				</CardContent>
				<CardActions>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/tickets/${ticketId}/edit`}>
						<EditIcon />
					</IconButton>

					<IconButton component={Link} sx={{ mr: 3 }} to={`/tickets/${ticketId}/delete`}>
						<DeleteForeverIcon sx={{ color: "red" }} />
					</IconButton>
				</CardActions>
			</Card>
		</Container>
	);
};

