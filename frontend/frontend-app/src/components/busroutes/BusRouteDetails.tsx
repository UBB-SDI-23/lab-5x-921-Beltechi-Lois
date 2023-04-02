import { Card, CardActions, CardContent, IconButton } from "@mui/material";
import { Container } from "@mui/system";
import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
//import { BACKEND_API_URL } from "../../constants";
import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import { Passenger } from "../../models/Passenger";
import { BusRoute } from "../../models/BusRoute";

export const BusRouteDetails = () => {
	//const { passengerId } = useParams();
    const { busRouteId } = useParams<{ busRouteId: string }>();
	const [busroute, setBusRoute] = useState<BusRoute>();

	useEffect(() => {
		const fetchBusRoute = async () => {
			// TODO: use axios instead of fetch
			// TODO: handle errors
			// TODO: handle loading state
			//const response = await fetch("http://localhost:8080/passengers/{passengerId}");
            //const response = await fetch(`http://localhost:8080/passengers/${Long(passengerId)}`);
            const response = await fetch(`http://localhost:8080/busroutes/${busRouteId}`); //sau pui cu ""
            
			const busroute = await response.json();
			setBusRoute(busroute);
            console.log(busroute);
		};
		fetchBusRoute();
	}, [busRouteId]);

	return (
		<Container>
			<Card>
				<CardContent>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/busroutes`}>
						<ArrowBackIcon />
					</IconButton>{" "}
					<h1>BusRoute Details</h1>
					<p>BusRoute Name: {busroute?.bus_name}</p>
					<p>BusRoute Type: {busroute?.route_type}</p>
					<p>BusRoute departureHour: {busroute?.departure_hour}</p>
                    <p>BusRoute arrivalHour: {busroute?.arrival_hour}</p>
                    <p>BusRoute distance: {busroute?.distance}</p>
					<p>BusRoute's people:</p>
					<ul>
						{busroute?.people?.map((person) => (
							<li key={person.id}> {person.lastName} {person.gender} {person.phoneNumber}</li>
						))}
					</ul>
				</CardContent>
				<CardActions>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/busroutes/${busRouteId}/edit`}>
						<EditIcon />
					</IconButton>

					<IconButton component={Link} sx={{ mr: 3 }} to={`/busroutes/${busRouteId}/delete`}>
						<DeleteForeverIcon sx={{ color: "red" }} />
					</IconButton>
				</CardActions>
			</Card>
		</Container>
	);
};

function Long(passengerId: string | undefined) {
    throw new Error("Function not implemented.");
}
