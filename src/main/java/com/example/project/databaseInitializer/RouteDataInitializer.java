package com.example.project.databaseInitializer;

import com.example.project.dtos.CityDto;
import com.example.project.dtos.bingMapsResponse.BingMapsResponse;
import com.example.project.dtos.bingMapsResponse.Resource;
import com.example.project.exceptions.DuplicateEntityException;
import com.example.project.mappers.CityMapper;
import com.example.project.models.Route;
import com.example.project.services.BingMapsRestService;
import com.example.project.services.CityService;
import com.example.project.services.RouteService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RouteDataInitializer implements IDataInitializer {
    public final RouteService routeService;
    public final BingMapsRestService bingMapsRestService;
    public final CityService cityService;
    public final CityMapper cityMapper;
    public final Logger logger;

    @Override
    public void initialize() {
        try {
            if (routeService.getCount() > 0) {
                throw new DuplicateEntityException(Route.class);
            }
            List<CityDto> cities = cityService.getAll();
            for (CityDto cityFrom : cities) {
                for (CityDto cityTo : cities) {
                    if (cityFrom.getId() < cityTo.getId()) {
                        try {
                            BingMapsResponse bingMapsResponse = bingMapsRestService.getRouteDetails(
                                    cityFrom.getName(), cityTo.getName());
                            Resource bingResource = bingMapsResponse.getResourceSets().get(0).getResources().get(0);
                            Integer bingDistance = Math.round(bingResource.getTravelDistance());
                            Integer bingDuration = Math.round(bingResource.getTravelDuration());

                            // direct route
                            Route directRoute = new Route();
                            directRoute.setCityFrom(cityMapper.cityDtoToCity(cityFrom));
                            directRoute.setCityTo(cityMapper.cityDtoToCity(cityTo));
                            directRoute.setDistance(bingDistance);
                            directRoute.setDuration(bingDuration);
                            routeService.save(directRoute);

                            // return route
                            Route returnRoute = new Route();
                            returnRoute.setCityFrom(cityMapper.cityDtoToCity(cityTo));
                            returnRoute.setCityTo(cityMapper.cityDtoToCity(cityFrom));
                            returnRoute.setDistance(bingDistance);
                            returnRoute.setDuration(bingDuration);
                            routeService.save(returnRoute);
                        } catch (Exception ignored) {
                            logger.error(String.format("Cannot get distance between %s and %s",
                                    cityFrom.getName(), cityTo.getName()));
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.info("[RouteDataInitializer] " + e.getMessage());
        }
    }
}
