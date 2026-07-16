# Standing instructions for any agent working in this project

## Context
This is LoreKeeper — a personal book/comic tracker backend, built with Spring Boot,
by a developer who is **actively learning backend development while building it**.
Code quality and correct reasoning matter more than speed. Treat this project the
way a careful senior engineer would treat a junior's real production codebase —
not a throwaway prototype.

**Full requirements, database schema, and API contract are defined in
`LoreKeeper.md` at the project root.** Read the relevant section
before implementing any new feature area — do not invent schema details or
endpoint shapes that contradict what's already specified there.

**CRITICAL RULES: You MUST also read and strictly adhere to all rule files located in `.agents/rules/` before taking any action. Specifically, you must follow:**
- `.agents/rules/coding-standards.md`
- `.agents/rules/git-workflow.md`

## Before writing any non-trivial code
1. State a short plan first: which files will be touched, and why. Wait for
   confirmation before proceeding if the change touches the database schema,
   adds a new dependency, or changes an existing API contract.
2. Never skip straight from "here's what you asked for" to a wall of code.
   A one-paragraph plan costs nothing and catches misunderstandings early.

## Never fabricate framework details
If you are not fully certain about an exact Spring Boot annotation, method
signature, property name, or version-specific behavior — say so explicitly
and name where to verify it (docs.spring.io), instead of confidently guessing.
A wrong but confident answer is worse than an honest "not sure, verify this."

## Follow the existing project structure exactly
Every resource in this codebase follows the same four-layer pattern:
`entity/` → `dto/` → `repository/` → `controller/`, plus `exception/` for shared
error handling. Do not invent a different structure for a new feature. If you
think a genuinely different pattern is warranted, explain why and ask first —
don't just silently do something else.

- Entities: private fields, `@NotBlank`/other validation on required fields,
  standard getters/setters (`isX()` for booleans, not `getX()`).
- Never return an `@Entity` directly from a controller — always convert to a
  matching `*ResponseDTO` first.
- Constructor injection only. Never field injection (`@Autowired` on a field).
- New "not found" / "invalid input" cases should route through the existing
  `GlobalExceptionHandler` pattern (`ResponseStatusException`), not a new
  ad-hoc error-handling style.

## No scope creep
Implement exactly what was asked. If you notice something else worth doing
along the way, mention it as a suggestion at the end of your response —
do not just start doing it unprompted.

## Git hygiene
- Conventional Commits format: `type(scope): subject` (e.g. `feat(chapter): add PATCH endpoint for read status`)
- No vague messages like "fix", "wip", "updates"
- One logical change per commit
- Never commit directly to `main` — work on a feature branch

## TODOs
Never leave a bare `// TODO` with no explanation. Every TODO must state
*what* is missing and *why* it was deferred, e.g.:
`// TODO: add pagination once the book list grows past ~50 items — not needed yet`

## Testing
Any new endpoint or non-trivial service method should get at least a basic
test alongside it in the same change — not deferred to "later."

## Explain non-trivial decisions
When you make a real design choice (not just syntax), briefly explain the
reasoning in your response — not just the code. The human is learning; a
silent correct answer teaches less than a briefly-justified one.
