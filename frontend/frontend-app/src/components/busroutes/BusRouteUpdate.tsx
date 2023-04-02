import { Button, Card, CardActions, CardContent, Container, IconButton, TextField } from "@mui/material";
import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import axios from "axios";
import { GlobalURL } from "../../main";


export const BusRouteUpdate = () => {

const navigate = useNavigate();

    const { busRouteId } = useParams();
	const [busroute, setBusRoute] = useState({
        bus_name: "",
        route_type: "",
        departure_hour: "",
        arrival_hour: "",
        distance: "",
    });

    useEffect(() => {
		const fetchBusRoute = async () => {
			const response = await fetch(`${GlobalURL}/busroutes/${busRouteId}`);
			const busroute = await response.json();
			setBusRoute(busroute);
            console.log(busroute);
		};
		fetchBusRoute();
	}, [busRouteId]);

	const updateBusRoute = async (event: { preventDefault: () => void }) => {
		event.preventDefault();
		try {
			await axios.put(`${GlobalURL}/busroutes/${busRouteId}`, busroute);
			//navigate(`/busroutes/${busRouteId}/`);
			navigate('/busroutes');
		} catch (error) {
			console.log(error);
		}
	};

	return (
		<Container>
			<Card>
				<CardContent>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/busroutes/${busRouteId}/`}>
						<ArrowBackIcon />
					</IconButton>{" "}
					<form onSubmit={updateBusRoute}>
						<TextField
							id="bus_name"
							label="Name"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setBusRoute({ ...busroute, bus_name: event.target.value })}
						/>
						<TextField
							id="route_type"
							label="Type"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setBusRoute({ ...busroute, route_type: event.target.value })}
						/>

                        <TextField
							id="departure_hour"
							label="Dept_Hour"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setBusRoute({ ...busroute, departure_hour: event.target.value })}
						/>

                        <TextField
							id="arrival_hour"
							label="Arr_Hour"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setBusRoute({ ...busroute, arrival_hour: event.target.value })}
						/>

                        <TextField
							id="distance"
							label="KM"
							variant="outlined"
							fullWidth
							sx={{ mb: 2 }}
							onChange={(event) => setBusRoute({ ...busroute, distance: (event.target.value) })}
						/>
                        
						<Button type="submit">Update BusRoute</Button>
					</form>
				</CardContent>
				<CardActions></CardActions>
			</Card>
		</Container>
	);
};