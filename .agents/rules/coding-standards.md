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
- **Lombok (required):** Do not write manual getters, setters, or constructors.
  Use `@Getter`, `@Setter`, `@NoArgsConstructor` (required by JPA), and
  optionally `@AllArgsConstructor` / `@Builder` for convenience.
  Do **not** use `@Data` on entities — its `equals()`/`hashCode()` generation
  causes infinite recursion with bidirectional JPA relationships.

## Controllers
- One controller per resource
- Full CRUD methods follow existing status-code conventions:
  `200` success, `400` bad input, `404` not found, `500` reserved for genuine
  unexpected server errors only — never used as a catch-all
- Every "not found" case throws `ResponseStatusException(HttpStatus.NOT_FOUND, "...")`,
  handled centrally by `GlobalExceptionHandler` — do not build a new local
  try/catch error-response pattern in an individual controller

## DTOs
- **Lombok (required):** Use `@Data`, `@NoArgsConstructor`, and
  `@AllArgsConstructor` instead of writing manual getters, setters, and
  constructors. `@Builder` is also encouraged for DTOs with many fields.
- `@Data` is safe and encouraged on DTOs (unlike entities, DTOs have no
  JPA lifecycle or bidirectional relationship issues).

## Repositories
- Interfaces only, extending `JpaRepository<Entity, Long>`
- Use built-in `JpaRepository` methods (`findById`, `findAll`, `save`,
  `deleteById`, etc.) as-is for standard CRUD — do not rewrite these
- When a custom query is needed (anything the built-in methods don't cover),
  use explicit `@Query` with JPQL. Do not use Spring Data derived method
  names (e.g. `findByTitleAndAuthor`) — `@Query` makes the SQL visible,
  predictable, and resilient to field renames

## Enums
- Any field with a fixed set of allowed values (e.g. format, status, provider)
  must be a Java enum with `@Enumerated(EnumType.STRING)` — never a raw `String`
  with a comment listing the allowed values

## API design
- URLs represent resources (nouns), never actions (verbs)
- Use `/users/me/...` for authenticated-user-scoped resources
- Use query parameters for filtering, searching, and pagination — not separate endpoints
- Path variable names must be unambiguous (e.g. `{libraryEntryId}`, not bare `{id}`
  when the resource could be confused with another)
- Local DB endpoints and external API proxy endpoints must live under different
  URL prefixes (e.g. `/books` for local catalog, `/open-library` for external lookups)
- One controller per resource concern — do not mix local DB operations and
  external API proxying in the same controller

## Logging & Auth context
- Do not pass `Principal` or `Authentication` into individual controller/service
  methods just to extract the current user for logging.
- Use SLF4J MDC (Mapped Diagnostic Context): a servlet `Filter` or Spring
  `HandlerInterceptor` should extract the authenticated user's identity from
  the security context once per request and put it into the MDC
  (e.g. `MDC.put("userId", ...)`). All log lines in that request thread will
  then automatically include the user context without any method parameter.
- Clear the MDC in a `finally` block (or filter cleanup) to prevent thread-pool
  leakage.

## Comments
- Add short, explanatory comments for non-obvious code — annotations,
  relationships, query logic, and any "why" that isn't immediately clear
  from reading the code itself.
- Do not comment the obvious (e.g. `// set title` above `setTitle()`).
- Every class should have a brief one-line comment or Javadoc explaining
  its purpose (e.g. `// Handles CRUD operations for the Novel resource`).
- Inline comments should explain **why**, not **what** — the code already
  shows what it does.
