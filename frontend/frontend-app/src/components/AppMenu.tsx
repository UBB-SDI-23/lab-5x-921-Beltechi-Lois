import {
  Box,
  AppBar,
  Toolbar,
  IconButton,
  Typography,
  Button,
} from "@mui/material";
import { Link, useLocation } from "react-router-dom";
import SchoolIcon from "@mui/icons-material/School";
import LocalLibraryIcon from "@mui/icons-material/LocalLibrary";
import Diversity3Icon from "@mui/icons-material/Diversity3";
import BusIcon from "@mui/icons-material/DirectionsBus";
import FilterIcon from "@mui/icons-material/Filter";
import BarChartIcon from "@mui/icons-material/BarChart";
import ListAltIcon from "@mui/icons-material/ListAlt";
import StreetviewIcon from '@mui/icons-material/Streetview';
import MapIcon from "@mui/icons-material/Map";





export const AppMenu = () => {
  const location = useLocation();
  const path = location.pathname;

  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="fixed" sx={{backgroundColor: "#A0522D", marginBottom: "20px" }}> 
	  {/*  #FF6600*/}
        <Toolbar>
          <IconButton
            component={Link}
            to="/"
            size="large"
            edge="start"
            color="inherit"
            aria-label="bus"
            sx={{ mr: 2 }}
          >
            <BusIcon />
          </IconButton>
          <Typography variant="h6" component="div" sx={{ mr: 5 }}>
            BusRoutes Management
          </Typography>
          <Button
            variant={path.startsWith("/busroutes") ? "outlined" : "text"}
            to="/busroutes"
            component={Link}
            color="inherit"
            sx={{ mr: 5 }}
            startIcon={<ListAltIcon />}
          >
            BusRoutes
          </Button>

          <Button
            variant={path.startsWith("/distance") ? "outlined" : "text"}
            to="/distance/filterBusRoutes"
            component={Link}
            color="inherit"
            sx={{ mr: 5 }}
            startIcon={<FilterIcon />}
          >
            Filter Busroutes
          </Button>

  
        </Toolbar>
      </AppBar>
    </Box>
  );
};
