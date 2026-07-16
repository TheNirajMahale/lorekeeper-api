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
- Commit message accurately describes what changed and why
