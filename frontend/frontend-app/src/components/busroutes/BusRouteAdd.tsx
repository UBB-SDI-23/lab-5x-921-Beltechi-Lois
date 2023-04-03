import { Button, Card, CardActions, CardContent, Container, IconButton, TextField } from "@mui/material";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import axios from "axios";
import { GlobalURL } from "../../main";
import { BACKEND_API_URL } from "../../constants";




export const BusRouteAdd = () => {

const navigate = useNavigate();

	const [busroute, setBusRoute] = useState({
        bus_name: "",
        route_type: "",
        departure_hour: "",
        arrival_hour: "",
        distance: "",
	});

	const addBusRoute = async (event: { preventDefault: () => void }) => {
		event.preventDefault();
		try {
			//await axios.post(`http://localhost:8080/busroutes`, busroute);
			//await axios.post(`${GlobalURL}/busroutes`, busroute);
			await axios.post(`${BACKEND_API_URL}/busroutes`, busroute);

			//await axios.post(`../api/busroutes`, busroute);

			navigate("/busroutes");
		} catch (error) {
			console.log(error);
		}
	};

	return (
		<Container>
			<Card>
				<CardContent>
					<IconButton component={Link} sx={{ mr: 3 }} to={`/busroutes`}>
						<ArrowBackIcon />
					</IconButton>{" "}
					<form onSubmit={addBusRoute}>
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
                        

						<Button type="submit">Add BusRoute</Button>
					</form>
				</CardContent>
				<CardActions></CardActions>
			</Card>
		</Container>
	);
};