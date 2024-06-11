package com.example.nutrimatebackend.dtos.food;

import com.example.nutrimatebackend.controllers.FoodController;
import com.example.nutrimatebackend.entities.Food;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class FoodAssembler implements RepresentationModelAssembler<Food, FoodDTOResponse>
{

    final PagedResourcesAssembler<Food> pagedResourcesAssembler;
    final FoodConverter foodConverter;

    @Override
    public FoodDTOResponse toModel(Food entity) {
        FoodDTOResponse resource = foodConverter.convertToDtoResponse(entity);
        addLinks(resource);
        return resource;
    }

    @Override
    public CollectionModel<FoodDTOResponse> toCollectionModel(Iterable<? extends Food> entities) {
        CollectionModel<FoodDTOResponse> resources = RepresentationModelAssembler.super.toCollectionModel(entities);
        addLinks(resources);
        return resources;
    }

    public PagedModel<FoodDTOResponse> toPagedModel(Page<Food> page) {
        PagedModel<FoodDTOResponse> resources = pagedResourcesAssembler.toModel(page, this);
        addLinks(resources);
        return resources;
    }

    public void addLinks(FoodDTOResponse resource) {
        resource.add(linkTo(methodOn(FoodController.class).getFoodById(resource.getId())).withSelfRel());
    }

    public void addLinks(CollectionModel<FoodDTOResponse> resources) {
        resources.add(linkTo(methodOn(FoodController.class).getAllFoodPaginated(null)).withSelfRel());
    }
}
