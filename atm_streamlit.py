import streamlit as st

# Transaction class to store details
class Transaction:
    def __init__(self, t_type, amount, balance_after):
        self.type = t_type
        self.amount = amount
        self.balance_after = balance_after

    def __str__(self):
        return f"{self.type}: ${self.amount}, New Balance: ${self.balance_after}"


# Initialize session state variables
if "balance" not in st.session_state:
    st.session_state.balance = 0.0
if "pin_verified" not in st.session_state:
    st.session_state.pin_verified = False
if "transaction_history" not in st.session_state:
    st.session_state.transaction_history = []


# Helper Functions
def verify_pin(pin):
    correct_pin = 1234
    return pin == correct_pin


def withdraw(amount):
    if amount > st.session_state.balance:
        return "Insufficient balance."
    elif amount <= 0:
        return "Enter a valid amount."
    else:
        st.session_state.balance -= amount
        st.session_state.transaction_history.append(
            Transaction("Withdraw", amount, st.session_state.balance)
        )
        return f"Withdrawal successful. New balance: ${st.session_state.balance}"


def deposit(amount):
    if amount <= 0:
        return "Enter a valid amount."
    else:
        st.session_state.balance += amount
        st.session_state.transaction_history.append(
            Transaction("Deposit", amount, st.session_state.balance)
        )
        return f"Deposit successful. New balance: ${st.session_state.balance}"


def transfer(account, amount):
    if len(account) != 12:
        return "Invalid account number."
    elif amount > st.session_state.balance:
        return "Insufficient balance."
    elif amount <= 0:
        return "Enter a valid amount."
    else:
        st.session_state.balance -= amount
        st.session_state.transaction_history.append(
            Transaction(f"Transfer to {account}", amount, st.session_state.balance)
        )
        return f"Transfer successful. New balance: ${st.session_state.balance}"


def get_transaction_history():
    if not st.session_state.transaction_history:
        return ["No transactions yet."]
    return [str(t) for t in st.session_state.transaction_history]


# Streamlit Interface
st.title("ATM Machine Interface")

# PIN Verification
if not st.session_state.pin_verified:
    st.sidebar.title("Enter PIN")
    entered_pin = st.sidebar.text_input("PIN", type="password")

    if st.sidebar.button("Submit"):
        if verify_pin(int(entered_pin)):
            st.session_state.pin_verified = True
            st.success("PIN verified! Welcome to your ATM.")
        else:
            st.error("Invalid PIN. Please try again.")
else:
    # ATM Menu Options
    option = st.selectbox(
        "Choose an option:",
        [
            "Select an option",
            "Check Balance",
            "Withdraw Money",
            "Deposit Money",
            "Transfer Money",
            "View Transaction History",
            "Exit",
        ],
    )

    if option == "Check Balance":
        st.info(f"Your current balance is: ${st.session_state.balance}")

    elif option == "Withdraw Money":
        withdraw_amount = st.number_input("Enter amount to withdraw:", min_value=0.0)
        if st.button("Withdraw"):
            result = withdraw(withdraw_amount)
            st.success(result)

    elif option == "Deposit Money":
        deposit_amount = st.number_input("Enter amount to deposit:", min_value=0.0)
        if st.button("Deposit"):
            result = deposit(deposit_amount)
            st.success(result)

    elif option == "Transfer Money":
        transfer_account = st.text_input("Enter 12-digit account number:")
        transfer_amount = st.number_input("Enter amount to transfer:", min_value=0.0)
        if st.button("Transfer"):
            result = transfer(transfer_account, transfer_amount)
            st.success(result)

    elif option == "View Transaction History":
        st.subheader("Transaction History")
        history = get_transaction_history()
        for entry in history:
            st.write(entry)

    elif option == "Exit":
        st.warning("Thank you for using the ATM. Goodbye!")
        st.session_state.pin_verified = False  # Reset PIN verification
