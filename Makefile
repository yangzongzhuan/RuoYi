.PHONY: verify

PYTHON ?= python3

verify:
	$(PYTHON) .agents/skills/code-change-verification/scripts/run_validation.py
