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

export const BusRouteDetails = () => {
    const { busRouteId } = useParams<{ busRouteId: string }>();
	const [busroute, setBusRoute] = useState<BusRoute>();

	useEffect(() => {
		const fetchBusRoute = async () => {
		

            //const response = await fetch(`${GlobalURL}/busroutes/${busRouteId}`);
			const response = await fetch(`${BACKEND_API_URL}/busroutes/${busRouteId}`);
			//const response = await fetch(`../api/busroutes/${busRouteId}`);
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

					{busroute && busroute.people && busroute.people.length > 0 ? (
					<ul>
						{busroute?.people?.map((person) => (
							<li key={person.id}> {person.lastName} {person.gender} {person.phoneNumber}</li>
						))}
					</ul>
					) : 
					// (
					// 	<p>No people transported yet</p>
					// )
					null}


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

