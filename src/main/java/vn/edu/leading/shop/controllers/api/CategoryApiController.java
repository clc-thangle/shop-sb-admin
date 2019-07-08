package vn.edu.leading.shop.controllers.api;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import vn.edu.leading.shop.controllers.BaseController;
import vn.edu.leading.shop.controllers.api.dto.CategoryDTO;
import vn.edu.leading.shop.errors.ObjectNotFoundException;
import vn.edu.leading.shop.models.CategoryModel;
import vn.edu.leading.shop.securities.CurrentUser;
import vn.edu.leading.shop.services.BaseService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryApiController extends BaseController<CategoryModel> {

    private final PagedResourcesAssembler assembler;

    public CategoryApiController(BaseService<CategoryModel> baseService, PagedResourcesAssembler assembler) {
        super(baseService);
        this.assembler = assembler;
    }

    @GetMapping
    public ResponseEntity<?> list(@CurrentUser Authentication authentication, Pageable pageable) {
        System.out.println(authentication.getName());
        return new ResponseEntity<>(assembler.toResource(baseService.findAll(pageable)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        CategoryModel categoryModel = baseService.findById(id).orElseThrow(() -> new ObjectNotFoundException("category"));
        return new ResponseEntity<>(categoryModel, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity save(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setCategoryName(categoryDTO.getCategoryName());
        categoryModel.setDescription(categoryDTO.getDescription());
        return new ResponseEntity(baseService.save(categoryModel), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryModel categoryModel = baseService.findById(id).orElseThrow(() -> new ObjectNotFoundException("category"));
        categoryModel.setCategoryName(categoryDTO.getCategoryName());
        categoryModel.setDescription(categoryDTO.getDescription());
        return new ResponseEntity(baseService.save(categoryModel), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        baseService.findById(id).orElseThrow(() -> new ObjectNotFoundException("category"));
        return new ResponseEntity<>(baseService.delete(id), HttpStatus.OK);
    }
}
