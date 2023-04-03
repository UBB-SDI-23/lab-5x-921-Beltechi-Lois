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

import EditIcon from "@mui/icons-material/Edit";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import { useEffect, useState } from "react";
import { GlobalURL } from "../../main";
import { Link } from "react-router-dom";
import AddIcon from "@mui/icons-material/Add";
import { BusRoutePeopleDTO } from "../../models/BusRoutePeopleDTO";

export const BusRouteStatistic = () => {
    const[loading, setLoading] = useState(true)
    const [busroutes, setBusRoutes] = useState([]);

    useEffect(() => {
    //fetch(`${GlobalURL}/busroutes/order-people-transported`)
    fetch(GlobalURL + "/busroutes/order-people-transported")

        .then(res => res.json())
        .then(data => {setBusRoutes(data); setLoading(false);})
    }, []);

    console.log(busroutes);

    
    return (
    <Container>
        <h1>All BusRoutes Ordered By The Number Of People transported</h1>

        {loading && <CircularProgress />}

        {!loading && busroutes.length == 0 && <div>No busRoutes found</div>}

        {!loading && busroutes.length > 0 && (

            <TableContainer component={Paper}>
                <Table sx={{ minWidth: 800 }} aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell>#</TableCell>
                            <TableCell align="center">BusRouteID</TableCell>
                            <TableCell align="center">BusName</TableCell>
                            <TableCell align="center">BusRouteType</TableCell>
                            <TableCell align="center">Number Of People</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {busroutes.map((busroutes:BusRoutePeopleDTO, index) => (
                            // <TableRow key={busroutes.bus_route_id}>
                            // <TableRow key={`${busroutes.bus_route_id}-${index}`}>
                            <TableRow key={busroutes.id}>
                                <TableCell component="th" scope="row">
                                    {index + 1}
                                </TableCell>
                                <TableCell align="center">{busroutes.busRouteId}</TableCell>
                                <TableCell align="center">{busroutes.busName}</TableCell>
                                <TableCell align="center">{busroutes.busType}</TableCell>
                                <TableCell align="center">{busroutes.noOfPeopleTransported}</TableCell>
                            </TableRow>
                        ))}
                </TableBody>
                </Table>
            </TableContainer>
        )
        }
    </Container>
        
    );       
};