import './App.css'
import {useEffect, useState} from "react";
import api from './services/api'
import axios from "axios";


function App() {
  const [loanAmount, setLoanAmount] = useState<number>(2000)
  const [loanPeriod, setLoanPeriod] = useState<number>(12)
  const [personalCode, setPersonalCode] = useState<string>("")

  const [decision, setDecision] = useState<any>(null);
  const [error, setError] = useState<string>("");

  useEffect(() => {
    if (!personalCode.trim()) {
      setDecision(null);
      setError("Enter a personal code.")
      return;
    }

    const timer = setTimeout(async () => {
      try {
        setDecision(null);
        setError("");

        if (personalCode.trim().length !== 11) {
          setDecision(null);
          setError("The personal code needs to be 11 numbers long!");
          return;
        }

        const response = await api.post("/loan/decision", {
          personalCode,
          loanAmount,
          loanPeriod,
        });

        setDecision(response.data);
      } catch (e) {
        if (axios.isAxiosError(e)) {
          setError(e.response?.data?.message || e.message || "Failed to fetch decision");
        }
      }
    }, 300);

    return () => clearTimeout(timer);
  }, [loanAmount, loanPeriod]);

  return (
      <div className="search">
        <p>Personal code</p>
        <input type="text" value={personalCode} onChange={event => setPersonalCode(event.target.value)}/>
        <p>Loan amount: {loanAmount}</p>
        <input
            type="range"
            min={2000}
            max={10000}
            step={100}
            value={loanAmount}
            onChange={event => setLoanAmount(Number(event.target.value))}
        />


        <p>Loan period: {loanPeriod}</p>
        <input
            type="range"
            min={12}
            max={60}
            step={1}
            value={loanPeriod}
            onChange={event => setLoanPeriod(Number(event.target.value))}
        />
        {error && <p className="error-text">{error}</p>}
        <div className="decision-card">
          <p><strong>Approved amount:</strong> {decision?.loanAmount ?? "N/A"}</p>
          <p><strong>Loan period:</strong> {decision?.loanPeriod ?? "N/A"} months</p>
        </div>
      </div>
  )
}

export default App
