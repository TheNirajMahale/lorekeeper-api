# Git workflow — always loaded

## Commit messages
Format: `type(scope): short description`

Allowed types: `feat`, `fix`, `refactor`, `test`, `docs`, `chore`

Examples:
- `feat(chapter): add PATCH endpoint for marking read status`
- `fix(novel): correct id-reuse bug in PUT handler`
- `refactor(exception): centralize 404 handling in GlobalExceptionHandler`

Not allowed: `wip`, `fix bug`, `updates`, `stuff`, or any message that doesn't
describe the actual change.

## Branching
- Never commit directly to `main`
- One feature/fix per branch, named `feature/short-description` or `fix/short-description`
- Keep branches short-lived — merge or discard, don't let them go stale

## Before considering a change "done"
- Code compiles and runs
- At least a basic test exists for new logic
- No leftover debug prints, commented-out old code, or unexplained TODOs
- The agent MUST test all new endpoints locally using `curl` commands and verify they work FIRST.
- After the agent's `curl` tests pass, the agent MUST guide the user to manually test all new endpoints in their API client (like Bruno).
- The agent MUST provide step-by-step instructions for the user on what to configure (URL, Method, Auth, Payload) and what exactly to check for in the expected response.
- The agent MUST provide these manual testing instructions strictly **ONE test at a time**, waiting for the user to confirm success before moving to the next test.
- **ONLY AFTER** both the AI and the user have successfully tested the endpoints is the agent allowed to `git commit` the code.
- Commit message accurately describes what changed and why

## Keeping documentation in sync
- When endpoints are added, removed, or renamed, update Section 5 of
  `LoreKeeper.md` in the same commit — stale API docs are worse than no docs.

## Branch lifecycle
- Delete feature branches (local and remote) after merging into `main`.
- Do not leave stale branches sitting around — they cause confusion about
  what is current.
