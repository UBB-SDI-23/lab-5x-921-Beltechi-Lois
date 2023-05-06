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

export const LuggageDetails = () => {
    const { luggageId } = useParams<{ luggageId: string }>();
	const [luggage, setLuggage] = useState<Luggage>();

	useEffect(() => {
		const fetchLuggage = async () => {
		

			const response = await fetch(`${BACKEND_API_URL}/luggages/${luggageId}`);
			const busroute = await response.json();
			setLuggage(busroute);
            console.log(busroute);
		};
		fetchLuggage();
	}, [luggageId]);

	return (
		<Container>
			<Card>
				<CardContent>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/luggages`}>
						<ArrowBackIcon />
					</IconButton>{" "}
					<h1>Luggage Details</h1>
					<p>Luggage Type: {luggage?.type}</p>
					<p>Luggage weight: {luggage?.weight}</p>
					<p>Luggage color: {luggage?.color}</p>
                    <p>Luggage priority: {luggage?.priority}</p>
                    <p>Luggage status: {luggage?.status}</p>
                    <p>Luggage description: {luggage?.description}</p>
                    <p>Luggage person assigned : {luggage?.person?.firstName}  {luggage?.person?.lastName}</p>



				</CardContent>
				<CardActions>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/luggages/${luggageId}/edit`}>
						<EditIcon />
					</IconButton>

					<IconButton component={Link} sx={{ mr: 3 }} to={`/luggages/${luggageId}/delete`}>
						<DeleteForeverIcon sx={{ color: "red" }} />
					</IconButton>
				</CardActions>
			</Card>
		</Container>
	);
};

