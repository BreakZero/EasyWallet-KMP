SHELL := /bin/bash

setup/pre-push-hook:
	mkdir -p .git/hooks
	cp .githooks/pre-push .git/hooks/pre-push
