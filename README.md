# aws-lambda

https://docs.github.com/en/actions/automating-builds-and-tests/building-and-testing-python

steps:
- uses: actions/checkout@v3
- name: Set up Python
  uses: actions/setup-python@v4
  with:
    python-version: '3.x'
- name: Install dependencies
  run: |
    python -m pip install --upgrade pip
    pip install -r requirements.txt
- name: Test with pytest
  run: |
    pip install pytest
    pip install pytest-cov
    pytest tests.py --doctest-modules --junitxml=junit/test-results.xml --cov=com --cov-report=xml --cov-report=html
