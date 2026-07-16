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

## Handling partial or ambiguous approval
If a message contains both a specific correction AND a vague blanket phrase
like "rest is ok", "rest is fine", or "the other changes are good" — **do not
treat that as permission to silently execute everything else.** Instead:
1. Apply/explain only the specific, unambiguous part first.
2. Explicitly restate, as a numbered list, exactly what "the rest" refers to —
   every remaining change you believe was approved.
3. Wait for explicit confirmation of that restated list before writing any
   code for those remaining items.

Example — if told "clear the point 1 explain, rest changes is ok":
- First, explain point 1 in plain language. Do not touch code yet.
- Then say: "Here is what I understand 'the rest' to mean: [1] ..., [2] ..., [3] ... — confirm before I implement these."
- Only proceed after the human replies to confirm.

Never expand a vague approval into more changes than were explicitly listed
back and confirmed.

## Never fabricate framework details
If you are not fully certain about an exact Spring Boot annotation, method
signature, property name, or version-specific behavior — say so explicitly
and name where to verify it (docs.spring.io), instead of confidently guessing.
A wrong but confident answer is worse than an honest "not sure, verify this."

## Architecture standard
Every resource in this project follows the same full pattern:
`Entity → RequestDTO/ResponseDTO → Repository → Service → Controller`, 
per `.agents/skills/new-feature-scaffold/SKILL.md`. There is no legacy exception. 
Do not invent a different structure.

- **Entities:** private fields, `@NotBlank`/other validation on required fields, standard getters/setters.
- **DTOs:** Never return an `@Entity` directly from a controller, and never accept one as a `@RequestBody` — always use matching DTOs.
- **Controllers & Services:** Constructor injection only. Never field injection (`@Autowired` on a field). Controllers only delegate to Services; business logic lives in the Service.
- **Error Handling:** Route "not found" / "invalid input" cases through the existing `GlobalExceptionHandler` pattern (`ResponseStatusException`), not a new ad-hoc style.

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
