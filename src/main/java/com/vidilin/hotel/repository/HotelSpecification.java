package com.vidilin.hotel.repository;

import com.vidilin.hotel.entity.Hotel;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class HotelSpecification {
    public static Specification<Hotel> hasName(String name) {
        return (root, query, cb) -> name == null ? null : cb.equal(cb.lower(root.get("name")), name.toLowerCase());
    }

    public static Specification<Hotel> hasBrand(String brand) {
        return (root, query, cb) -> brand == null ? null : cb.equal(cb.lower(root.get("brand")), brand.toLowerCase());
    }

    public static Specification<Hotel> hasCity(String city) {
        return (root, query, cb) -> city == null ? null : cb.equal(cb.lower(root.get("address").get("city")), city.toLowerCase());
    }

    public static Specification<Hotel> hasCountry(String country) {
        return (root, query, cb) -> country == null ? null : cb.equal(cb.lower(root.get("address").get("country")), country.toLowerCase());
    }

    public static Specification<Hotel> hasAmenities(List<String> amenities) {
        return (root, query, criteriaBuilder) -> {
            if (amenities == null || amenities.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            // JOIN с коллекцией amenities и поиск по совпадению с любым из списка
            Join<Hotel, String> amenitiesJoin = root.join("amenities");
            return amenitiesJoin.in(amenities);
        };
    }
}
