# Contribution / AI Development Workflow

1. Requirement: clarify acceptance criteria.
2. Specification: update `spec/` before implementation.
3. Plan: record tasks in `docs/implementation-plan.md` or the issue.
4. Implementation: use Cursor/Copilot under `.cursor/rules/` and `.github/copilot-instructions.md`.
5. Testing: run `mvn test` and `npm run build`.
6. Review: run the Cursor review commands and compare against `spec/`.
7. Fix: apply reviewed findings only.
8. Evidence: save material prompts under `.specstory/history/` and update the AI review log.
