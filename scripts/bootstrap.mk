SHELL := /bin/bash

setup/pre-push-hook:
	mkdir -p .git/hooks
	cp .githooks/pre-commit .git/hooks/pre-commit
