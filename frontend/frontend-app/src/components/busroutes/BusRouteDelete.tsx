import { Container, Card, CardContent, IconButton, CardActions, Button } from "@mui/material";
import { Link, useNavigate, useParams } from "react-router-dom";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import axios from "axios";
import { GlobalURL } from "../../main";
import { BACKEND_API_URL } from "../../constants";




export const BusRouteDelete = () => {
	const { busRouteId } = useParams();
	const navigate = useNavigate();

	const handleDelete = async (event: { preventDefault: () => void }) => {
		event.preventDefault();
		await axios.delete(`${BACKEND_API_URL}/busroutes/${busRouteId}`);
		// go to busroutes list
		navigate("/busroutes");
	};

	const handleCancel = (event: { preventDefault: () => void }) => {
		event.preventDefault();
		navigate("/busroutes");
	};

	return (
		<Container>
			<Card>
				<CardContent>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/busroutes`}>
						<ArrowBackIcon />
					</IconButton>{" "}
					Are you sure you want to delete this busRoute? This cannot be undone!
				</CardContent>
				<CardActions>
					<Button onClick={handleDelete}>Delete it</Button>
					<Button onClick={handleCancel}>Cancel</Button>
				</CardActions>
			</Card>
		</Container>
	);
};