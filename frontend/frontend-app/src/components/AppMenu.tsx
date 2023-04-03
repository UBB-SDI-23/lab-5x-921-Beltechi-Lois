import { Box, AppBar, Toolbar, IconButton, Typography, Button } from "@mui/material";
import { Link, useLocation } from "react-router-dom";
import SchoolIcon from "@mui/icons-material/School";
import LocalLibraryIcon from "@mui/icons-material/LocalLibrary";
import Diversity3Icon from "@mui/icons-material/Diversity3";


export const AppMenu = () => {
	const location = useLocation();
	const path = location.pathname;

	return (
		<Box sx={{ flexGrow: 1 }}>
			<AppBar position="fixed" sx={{ marginBottom: "20px" }}>
				<Toolbar>
					<IconButton
						component={Link}
						to="/"
						size="large"
						edge="start"
						color="inherit"
						aria-label="school"
						sx={{ mr: 2 }}>
						<SchoolIcon />
					</IconButton>
					<Typography variant="h6" component="div" sx={{ mr: 5 }}>
						BusRoutes management
					</Typography>
					<Button
						variant={path.startsWith("/busroutes") ? "outlined" : "text"}
						to="/busroutes"
						component={Link}
						color="inherit"
						sx={{ mr: 5 }}
						startIcon={<LocalLibraryIcon />}>
						BusRoutes
					</Button>

					<Button
						variant={path.startsWith("/distance") ? "outlined" : "text"}
						to="/distance/filterBusRoutes"
						component={Link}
						color="inherit"
						sx={{ mr: 5 }}
						startIcon={<Diversity3Icon />}>
						Filter Busroutes
					</Button>

					<Button
						variant={path.startsWith("/statistic/OrderByPeopleOnBusRoutes") ? "outlined" : "text"}
						to="/statistic/OrderByPeopleOnBusRoutes"
						component={Link}
						color="inherit"
						sx={{ mr: 5 }}
						startIcon={<Diversity3Icon />}>
						Statistic BusRoutes
					</Button>
				</Toolbar>
			</AppBar>
		</Box>
	);
};