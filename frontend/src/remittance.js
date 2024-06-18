import React, {useState} from 'react';

const Remittance = () => {
    // State for form fields
    const [senderName, setSenderName] = useState('');
    const [recipientName, setRecipientName] = useState('');
    const [amount, setAmount] = useState('');
    const [paymentDate, setPaymentDate] = useState('');

    const availableBalance = 1000; // To be replaced with logic to fetch balance dynamically from an API

    // Function to handle form submission
    const handleFormSubmit = (event) => {
        event.preventDefault();
        // To be added with logic to send the remittance data to a server or perform some other action based on the form inputs
        console.log('Form submitted:', senderName, recipientName, amount);
        // Reset form fields after submission
        setSenderName('');
        setRecipientName('');
        setAmount('');
    };

    return (
        <div className="remittance-page">
            <h1>Transfer</h1>
            <form onSubmit={handleFormSubmit}>
                <label>
                    From (Payer's Name):
                    <input
                        type="text"
                        value={senderName}
                        onChange={(e) => setSenderName(e.target.value)}
                        required
                    />
                </label>
                <br/>
                <label>
                    To (Payee's Name):
                    <input
                        type="text"
                        value={recipientName}
                        onChange={(e) => setRecipientName(e.target.value)}
                        required
                    />
                </label>
                <br/>
                <label>
                    Amount:
                    <input
                        type="number"
                        value={amount}
                        onChange={(e) => setAmount(e.target.value)}
                        required
                    />
                    <span>Available balance: {availableBalance.toFixed(2)} KES</span>
                </label>
                <br/>
                <label>
                    Payment Date:
                    <br/>
                    <input
                        type="date"
                        value={paymentDate}
                        onChange={(e) => setPaymentDate(e.target.value)}
                        required
                    />
                </label>
                <br/>
                <button type="submit">Send Money</button>
            </form>
        </div>
    );
};

export default Remittance;
