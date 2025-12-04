import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Voyage, AuditLog } from '../types';

// Determine API base URL from env or fall back to deployed Render URL
const API_BASE = import.meta.env.VITE_API_URL || 'https://auditsystem-jwb6.onrender.com';
const api = axios.create({ baseURL: API_BASE });

export const VoyageDashboard: React.FC = () => {
    const [voyages, setVoyages] = useState<Voyage[]>([]);
    const [logs, setLogs] = useState<AuditLog[]>([]);
    const [newShip, setNewShip] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const fetchData = async () => {
        try {
            setLoading(true);
            setError(null);
            const [voyageRes, logRes] = await Promise.all([
                api.get('/api/voyages'),
                api.get('/api/audit-logs')
            ]);
            setVoyages(voyageRes.data);
            setLogs(logRes.data);
        } catch (e: any) {
            setError(e?.message || 'Failed to load data');
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => { fetchData(); }, []);

    const handleCreate = async () => {
        if (!newShip.trim()) {
            alert('Please enter a ship name');
            return;
        }
        try {
            setLoading(true);
            await api.post('/api/voyages', {
                shipName: newShip,
                departurePort: 'Port Canaveral',
                departureDate: new Date().toISOString().split('T')[0],
                status: 'Scheduled'
            });
            setNewShip('');
            await fetchData(); // Refresh to show new voyage AND new audit log
        } catch (e: any) {
            alert('Failed to create voyage: ' + (e?.message || 'Unknown error'));
        } finally {
            setLoading(false);
        }
    };

    const handleClearVoyages = async () => {
        if (!confirm('Are you sure you want to delete ALL voyages?')) return;
        try {
            setLoading(true);
            await api.delete('/api/voyages');
            await fetchData();
        } catch (e: any) {
            alert('Failed to clear voyages: ' + (e?.message || 'Unknown error'));
        } finally {
            setLoading(false);
        }
    };

    const handleClearLogs = async () => {
        if (!confirm('Are you sure you want to delete ALL audit logs?')) return;
        try {
            setLoading(true);
            await api.delete('/api/audit-logs');
            await fetchData();
        } catch (e: any) {
            alert('Failed to clear logs: ' + (e?.message || 'Unknown error'));
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="p-6 max-w-5xl mx-auto">
            <div className="flex items-center justify-between mb-6">
                <h1 className="text-3xl font-bold text-blue-900">VoyageVista Manager</h1>
                <div className="flex gap-2">
                    <button onClick={handleClearVoyages} className="px-3 py-2 bg-red-600 text-white rounded" title="Delete all voyages">Clear Voyages</button>
                    <button onClick={handleClearLogs} className="px-3 py-2 bg-orange-600 text-white rounded" title="Delete all audit logs">Clear Logs</button>
                </div>
            </div>

            {error && (
                <div className="mb-4 p-3 border border-red-300 bg-red-50 text-red-700 rounded">{error}</div>
            )}
            {loading && (
                <div className="mb-4 text-gray-600">Loading...</div>
            )}

            {/* Voyage Creation Section */}
            <div className="mb-8 bg-gray-100 p-4 rounded-lg">
                <h2 className="text-xl font-semibold mb-2">Create New Voyage</h2>
                <div className="flex gap-2">
                    <input 
                        className="border p-2 rounded flex-grow"
                        value={newShip}
                        onChange={(e) => setNewShip(e.target.value)}
                        placeholder="Enter Ship Name (e.g. Disney Wish)"
                    />
                    <button 
                        onClick={handleCreate}
                        className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
                        disabled={loading}
                    >
                        {loading ? 'Working...' : 'Create Voyage'}
                    </button>
                </div>
                <div className="text-sm text-gray-500 mt-2">Voyages: {voyages.length} • Logs: {logs.length}</div>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                {/* Active Voyages List */}
                <div>
                    <h2 className="text-xl font-semibold mb-4">Active Voyages</h2>
                    {voyages.length === 0 ? (
                        <div className="text-gray-500 border p-3 rounded">No voyages yet. Create one above.</div>
                    ) : (
                        <ul className="space-y-2">
                            {voyages.map(v => (
                                <li key={v.id} className="border p-3 rounded shadow-sm">
                                    <div className="flex items-center justify-between">
                                        <div>
                                            <div className="font-semibold">{v.shipName}</div>
                                            <div className="text-xs text-gray-500">{v.departurePort || 'Unknown'} • {v.departureDate || ''}</div>
                                        </div>
                                        <span className="text-sm bg-green-100 text-green-800 px-2 py-1 rounded">{v.status}</span>
                                    </div>
                                </li>
                            ))}
                        </ul>
                    )}
                </div>

                {/* Audit Log Stream (The "Disney Requirement" Feature) */}
                <div>
                    <h2 className="text-xl font-semibold mb-4">System Audit Traceability</h2>
                    <div className="bg-slate-900 text-green-400 p-4 rounded h-64 overflow-y-auto font-mono text-sm">
                        {logs.length === 0 ? (
                            <div className="text-slate-400">No audit logs yet. Actions will appear here.</div>
                        ) : (
                            logs.map(log => (
                                <div key={log.id} className="mb-2 border-b border-slate-700 pb-1">
                                    <span className="text-xs text-slate-500">{new Date(log.timestamp).toLocaleString()}</span><br/>
                                    <span className="font-bold">[{log.action}]</span> {log.details}
                                </div>
                            ))
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
};
