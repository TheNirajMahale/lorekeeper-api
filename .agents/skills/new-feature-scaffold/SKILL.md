---
name: new-feature-scaffold
description: Scaffolds a new REST resource in this Spring Boot project, following the established Entity -> DTO -> Repository -> Controller pattern with validation and centralized exception handling. Use when adding a brand-new resource, entity, or feature to the backend (e.g. "add a Tags feature", "create a new UserBook resource").
---

# New Feature Scaffold

## Objective
Every resource in this project is built the same way, in the same order. This
skill enforces that consistency so new features don't drift into a different
shape than the rest of the codebase.

## Before scaffolding anything
State the plan first: entity name, its fields (with types and which are
required), and how it relates to existing entities (if at all). Wait for
confirmation before generating files if the relationship or fields are not
already unambiguous from the request.

## Build order (do not skip steps or reorder)

1. **Entity** (`entity/{Name}.java`)
   - `@Entity`, `@Table(name = "...")`
   - `@Id` + `@GeneratedValue(strategy = GenerationType.IDENTITY)`, type `Long`
   - `@NotBlank`/appropriate validation on required fields
   - Real relationship annotations (`@ManyToOne`, `@JoinColumn`, etc.) where applicable — never a bare `Long` foreign key field
   - Standard getters/setters, `isX()` for booleans

2. **DTO** (`dto/{Name}ResponseDTO.java`)
   - Mirrors only the fields a client should see
   - Never include internal-only fields directly copied from the entity

3. **Repository** (`repository/{Name}Repository.java`)
   - Interface only, `extends JpaRepository<{Name}, Long>`
   - Add derived query methods (`findByX`, `existsByX`) only if the feature actually needs them — don't add unused methods speculatively

4. **Controller** (`controller/{Name}Controller.java`)
   - Constructor injection of the repository (and any other needed repository/service)
   - Full CRUD unless the request specifies otherwise
   - `orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "..."))` for single-item lookups
   - A private `toDTO(...)` conversion method
   - `@Valid @RequestBody` on create/update methods

5. **Test** — at minimum, one test covering the "happy path" for each new endpoint, and one covering the 404/400 case

## After scaffolding
Summarize what was created and explicitly flag anything you weren't sure
about (e.g. "I assumed X should be optional — confirm this is right") rather
than silently picking an assumption and moving on.
