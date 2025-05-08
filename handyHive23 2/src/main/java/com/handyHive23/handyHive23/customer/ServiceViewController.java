// ServiceViewController.java (or you can put this in ServiceController if preferred)

package com.handyHive23.handyHive23.customer;

import com.handyHive23.handyHive23.customer.Customer;
import com.handyHive23.handyHive23.provider.Provider;
import com.handyHive23.handyHive23.provider.ProviderRepository;
import com.handyHive23.handyHive23.provider.ProviderService;
import com.handyHive23.handyHive23.service.ServicePost;
import com.handyHive23.handyHive23.service.ServiceRepository;
import com.handyHive23.handyHive23.service.ServiceService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/services")
public class ServiceViewController {

    private final ProviderService providerService;
    private final ProviderRepository providerRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    private final ServiceService serviceService;



    public ServiceViewController(ProviderService providerService, ProviderRepository providerRepository,ServiceService serviceService) {
        this.providerService = providerService;
        this.providerRepository = providerRepository;
        this.serviceService = serviceService;
    }

    @GetMapping("/find-services")
public String findServicesPage(@RequestParam(value = "search", required = false) String search,
                               HttpSession session,
                               Model model) {

    List<Provider> providers = providerRepository.findAll();

    if (search != null && !search.isEmpty()) {
        String lowerSearch = search.toLowerCase();
        String searchPrefix = lowerSearch.substring(0, Math.min(4, lowerSearch.length()));

        providers = providers.stream()
            .filter(p -> {
                String expertise = p.getExpertise() != null ? p.getExpertise().toLowerCase() : "";
                String name = p.getName() != null ? p.getName().toLowerCase() : "";
                String business = p.getBusinessName() != null ? p.getBusinessName().toLowerCase() : "";
                return expertise.contains(lowerSearch) || expertise.startsWith(searchPrefix) ||
                       name.contains(lowerSearch) || business.contains(lowerSearch);
            })
            .collect(Collectors.toList());
    }

    Map<String, Double> ratingsMap = new HashMap<>();
for (Provider p : providers) {
    double avgRating = providerService.getAverageRatingForProvider(p.getId());
    ratingsMap.put(String.valueOf(p.getId()), avgRating);
}

Map<String, Long> providerToFirstServiceId = new HashMap<>();
for (Provider p : providers) {
    List<ServicePost> services = serviceService.getServicesByProvider(p.getId());
    if (!services.isEmpty()) {
        providerToFirstServiceId.put(String.valueOf(p.getId()), services.get(0).getId());
    }
}
model.addAttribute("providerToFirstServiceId", providerToFirstServiceId);



    model.addAttribute("providers", providers);
    model.addAttribute("ratingsMap", ratingsMap);
    model.addAttribute("provider", session.getAttribute("provider"));
    model.addAttribute("customer", session.getAttribute("customer"));
    model.addAttribute("search", search);

    return "find-services";
}


}
