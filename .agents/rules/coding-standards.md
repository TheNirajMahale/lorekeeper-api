# Coding standards — always loaded

## Package structure (fixed, do not deviate without asking)
```
com.lorekeeper.lorekeeper_api
├── entity/       # @Entity classes only
├── dto/          # Request/response shapes for the client
├── repository/   # JpaRepository interfaces only — no logic bodies
├── controller/   # HTTP handling only — delegates to repository/service
├── service/      # Business logic that doesn't belong in a controller
└── exception/    # GlobalExceptionHandler and any custom exceptions
```

## Naming
- Classes: `PascalCase`
- Fields/methods: `camelCase`
- Boolean getters: `isX()`, never `getX()`
- DTOs: `{Entity}ResponseDTO` for outbound, `{Entity}RequestDTO` (or similar)
  for anything that needs a distinct inbound shape

## Entities
- `@Id` + `@GeneratedValue(strategy = GenerationType.IDENTITY)` for all primary keys
- IDs are `Long`, never `int`
- Required fields get `@NotBlank` (strings) or an appropriate `@NotNull`/range
  annotation — do not leave required fields unvalidated
- Relationships use real object references (`@ManyToOne` etc.), never bare
  foreign-key `Long` fields sitting next to a comment saying what they mean

## Controllers
- One controller per resource
- Full CRUD methods follow existing status-code conventions:
  `200` success, `400` bad input, `404` not found, `500` reserved for genuine
  unexpected server errors only — never used as a catch-all
- Every "not found" case throws `ResponseStatusException(HttpStatus.NOT_FOUND, "...")`,
  handled centrally by `GlobalExceptionHandler` — do not build a new local
  try/catch error-response pattern in an individual controller

## Repositories
- Interfaces only, extending `JpaRepository<Entity, Long>`
- Prefer Spring Data derived query methods (`findByX`, `existsByX`) over
  writing custom `@Query` unless the derived method name would be unreasonably
  long or the query is genuinely too complex to express that way
