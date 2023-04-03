


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
} from "@mui/material";
import React from "react";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
//import { BACKEND_API_URL } from "../../constants";
import ReadMoreIcon from "@mui/icons-material/ReadMore";
import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import AddIcon from "@mui/icons-material/Add";
import { BusRoute } from "../../models/BusRoute";
import { GlobalURL } from "../../main";
import { BACKEND_API_URL } from "../../constants";

export const BusRouteShowAll = () => {
	const [loading, setLoading] = useState(false);
	const [busroutes, setBusRoutes] = useState<BusRoute[]>([]);

	useEffect(() => {
		setLoading(true);
		//fetch("http://localhost:8080/busroutes")
		//fetch(`${GlobalURL}busroutes`)
		//fetch(`${GlobalURL}/busroutes`)
		fetch(`${BACKEND_API_URL}/busroutes`)
		//fetch(`api/busroutes`)
			.then((response) => response.json())
			.then((data) => {
				setBusRoutes(data);
				setLoading(false);
			});
	}, []);

	return (
		<Container>
			<h1>All BusRoutes</h1>

			{loading && <CircularProgress />}
			{!loading && busroutes.length === 0 && <p>No busroutes found</p>}
			{!loading && (
                <div style={{display:'flex', alignItems:'center'}}>
				<IconButton component={Link} sx={{ mr: 3 }} to={`/busroutes/add`}>
					<Tooltip title="Add a new busRoute" arrow>
						<AddIcon color="primary" />
					</Tooltip>
				</IconButton>
                </div>
			)}

            
			{!loading && busroutes.length > 0 && (
				<TableContainer component={Paper}>
					<Table sx={{ minWidth: 650 }} aria-label="simple table">
						<TableHead>
							<TableRow>
								<TableCell>#</TableCell>
								<TableCell align="right">bus_name</TableCell>
								<TableCell align="right">route_type</TableCell>
								<TableCell align="right">departure_hour</TableCell>
								<TableCell align="center">arrival_hour</TableCell>
                                <TableCell align="center">distance</TableCell>
                                <TableCell align="center">Options</TableCell>

							</TableRow>
						</TableHead>
						<TableBody>
							{busroutes.map((busroute, index) => (
								<TableRow key={busroute.id}>
									<TableCell component="th" scope="row">
										{index + 1}
									</TableCell>
									<TableCell component="th" scope="row">
										<Link to={`/busroutes/${busroute.id}/details`} title="View busRoute details">
											{busroute.bus_name}
										</Link>
									</TableCell>
                                    
                                    <TableCell align="right">{busroute.route_type}</TableCell>
                                    <TableCell align="right">{busroute.departure_hour}</TableCell>
                                    <TableCell align="right">{busroute.arrival_hour}</TableCell>
                                    <TableCell align="center">{busroute.distance}</TableCell>

									<TableCell align="right">
										<IconButton
											component={Link}
											sx={{ mr: 3 }}
											to={`/busroutes/${busroute.id}/details`}>
											<Tooltip title="View busRoute details" arrow>
												<ReadMoreIcon color="primary" />
											</Tooltip>
										</IconButton>

										<IconButton component={Link} sx={{ mr: 3 }} to={`/busroutes/${busroute.id}/edit`} title="Edit busRoute">
											<EditIcon />
										</IconButton>

										<IconButton component={Link} sx={{ mr: 3 }} to={`/busroutes/${busroute.id}/delete`} title="Delete busRoute">
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